package co.parrolabs.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class OktaClientConfiguration {

    @Value("${client.okta.url}")
    private String oktaDomain;

    @Value("${client.okta.clientId}")
    private String oktaClientId;

    @Value("${client.okta.clientSecret}")
    private String oktaClientSecret;
}