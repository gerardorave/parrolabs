package co.parrolabs.security.client;

import co.parrolabs.security.OktaTokenResponse;
import co.parrolabs.security.dto.OktaTokenDto;
import co.parrolabs.security.mock.TokenResponseOkta;
import co.parrolabs.security.util.WebfluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Predicate;

@Component
@Slf4j
public class OktaTokenClient {

    @Value("${client.okta.clientId}")
    private String oktaClientId;

    @Value("${client.okta.clientSecret}")
    private String oktaClientSecret;

    public static final String GRANT_TYPE = "grant_type";
    public static final String GRANT_TYPE_PASSWORD = "password";

    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";

    public static final String SCOPE_PARAMETER = "scope";
    public static final String SCOPE_OPENID = "openid";

    public static final String CLIENT_ID_PARAMETER = "client_id";
    public static final String CLIENT_SECRET_PARAMETER = "client_secret";

    //FORMAT: /dev-mox5llxuy7gee3qr.us.auth0.com/oauth/token for access to auth server custom claims on id_token
    public static final String OKTA_TOKEN_VALIDATE_PATH = "/dev-mox5llxuy7gee3qr.us.auth0.com/oauth/token";

    private final WebClient oktaClient;

    @Autowired
    public OktaTokenClient(
        @Value("${client.okta.url}")
        String oktaDomain
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        log.info("Setting up Okta Client with baseUrl: {}", oktaDomain);
        this.oktaClient = WebClient.builder()
                .baseUrl(oktaDomain)
                .defaultHeaders(req -> req.addAll(headers))
                .build();
    }

    public OktaTokenClient(WebClient oktaClient) {
        this.oktaClient = oktaClient;
    }

    @Cacheable(value = "userTokenCache", key = "#cacheKey", unless = "#result == null")
    public OktaTokenDto fetchTokenFromOkta(final String cacheKey, final String username, final String password) {

        OktaTokenDto oktaTokenDto = null;

        MultiValueMap<String, String> postBodyParameters = new LinkedMultiValueMap<>();
        postBodyParameters.add(GRANT_TYPE, GRANT_TYPE_PASSWORD);
        postBodyParameters.add(USERNAME_PARAMETER, username);
        postBodyParameters.add(PASSWORD_PARAMETER, password);
        postBodyParameters.add(SCOPE_PARAMETER, SCOPE_OPENID);
        postBodyParameters.add(CLIENT_ID_PARAMETER, oktaClientId);
        postBodyParameters.add(CLIENT_SECRET_PARAMETER, oktaClientSecret);

        try {
            log.info("Attempting to retrieve user token for user: {} at endpoint: {}", username, OKTA_TOKEN_VALIDATE_PATH);
            Predicate<HttpStatus> httpClientError = i->i.is4xxClientError();
            Predicate<HttpStatus> httpServerError = i->i.is5xxServerError();
            final ResponseEntity<OktaTokenResponse> responseEntity = oktaClient.post()
                      .uri(OKTA_TOKEN_VALIDATE_PATH)
                      .bodyValue(postBodyParameters)
                      .retrieve()
                      .onStatus(httpClientError, res -> {
                          log.info("Unable to validate credentials for user: {}", username);
                          return res.bodyToMono(Throwable.class);
                      })
                      .onStatus(httpServerError, res -> {
                          log.error("Server Error received while validating credentials for user: {}", username);
                          return res.bodyToMono(Throwable.class);
                      })
                      .toEntity(OktaTokenResponse.class)
                      .timeout(Duration.ofSeconds(3))
                      .retryWhen(
                              Retry
                                  .fixedDelay(2, Duration.ofMillis(300))
                                  .filter(WebfluxUtils::is5xxServerErrorOrTimeout)
                      )
                      .block();

            log.debug("Found user token for user: {}", username);
            if (responseEntity != null) {
                //responseEntity.getBody()==null to avoid create an oauth account. only for test MOCKED
                final OktaTokenResponse oktaTokenResponse = responseEntity.getBody()==null? TokenResponseOkta.oktaTokenResponseOauth():responseEntity.getBody();
                final String userToken = Objects.requireNonNull(oktaTokenResponse).getIdToken();

                oktaTokenDto = OktaTokenDto.builder()
                        .idToken(userToken)
                        .email(username)
                        .cacheKey(cacheKey)
                        .createdAt(Instant.now())
                        .build();

            }
        } catch (WebClientRequestException hcee) {
            //400 BAD REQUEST from OKTA indicates invalid credentials
            log.info("Unable to validate credentials for user: {}", username);
        } catch (Exception ex) {
            log.error("An unknown error has occurred when trying to validate username: {}", username, ex);
        }
        return oktaTokenDto;
    }

}
