package co.parrolabs.security.filter;

import co.parrolabs.security.User;
import co.parrolabs.security.client.OktaTokenClient;
import co.parrolabs.security.dto.OktaTokenDto;
import co.parrolabs.security.mock.UserJwtPayLoad;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;



@Slf4j
@Component
public class OktaBasicAuthenticationFilter extends GenericFilterBean {

    private final OktaTokenClient oktaTokenClient;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    public static final String BASIC = "Basic";
    public static final String SEPARATOR = ":";

    public OktaBasicAuthenticationFilter(OktaTokenClient oktaTokenClient) {
        this.oktaTokenClient = oktaTokenClient;
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final User user = findUserFromRequest(request);

        if (!Objects.isNull(user)) {
            log.info("Adding user:{} with id:{} to spring security context", user.getEmail(), user.getId());
            SecurityContextHolder.clearContext();
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, servletResponse);
    }

    private User findUserFromRequest(final HttpServletRequest request) {
        final String path = request.getRequestURI();
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Validate BASIC AUTH header format
        if (StringUtils.isEmpty(authHeader)) {
            log.info("No Authorization header found on request:{}", path);
            return null;
        }

        String usernamePassword = StringUtils.substringAfterLast(authHeader, BASIC);
        if (StringUtils.isEmpty(usernamePassword)) {
            log.info("No Basic Authorization value found on request:{}", path);
            return null;
        }

        //DECODE HEADER
        usernamePassword = usernamePassword.trim();
        usernamePassword = new String(Base64.getDecoder().decode(usernamePassword));

        final String username = StringUtils.substringBefore(usernamePassword, SEPARATOR);
        final String password = StringUtils.substringAfterLast(usernamePassword, SEPARATOR);

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            log.info("Username/password not found on Authorization header for request:{}", path);
            return null;
        }

        log.debug("Validating submitted username/password combination for request:{}", path);
        return validateUser(username, password);
    }


    public User validateUser(final String username, final String password) {

        User user = null;
        final String cacheKey = buildCacheKey(username, password);

        log.info("Fetching user token for user: {}", username);
        OktaTokenDto oktaTokenDto = oktaTokenClient.fetchTokenFromOkta(cacheKey, username, password);

        if (Objects.isNull(oktaTokenDto)) {
            log.info("Unable to authenticate user: {}", username);
            return null;
        }
        String userToken = oktaTokenDto.getIdToken();

        //Break apart into header(index=0) and payload(index=1) components
        final String[] chunks = userToken.split("\\.");

        log.debug("Decoding user token for user: {}", username);
        final String jwtPayload = new String(DECODER.decode(chunks[1]));

        log.debug("Transforming user token into Spring Security auth credentials for user: {}", username);
        try {
            user = OBJECT_MAPPER.readValue(jwtPayload, User.class);
            //user==null to avoid create an user account mock. only for test MOCKED
            user = (user == null || user.getEmail() == null) ? UserJwtPayLoad.userJwtMocked():user;
        } catch (JsonProcessingException jpe) {
            log.error("An error has occurred when trying to parse the token for username: {}", username, jpe);
        }
        return user;
    }

    private String buildCacheKey(final String username, final String password) {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append("-").append(DigestUtils.sha512Hex(password));
        return sb.toString();
    }

}