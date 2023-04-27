package co.parrolabs.swagger.config;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import static java.util.Collections.singletonList;

@Configuration
@Profile({"!UAT","!Production"})
public class SwaggerConfiguration {

    @Value("${service-order-customer.url}")
    private String swaggerBaseUrl;

    @Bean
    public Docket api() {
        String swaggerHost = StringUtils.substringAfterLast(swaggerBaseUrl, "://");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("co.parrolabs.controller"))
                .paths(PathSelectors.any())
                .build()
                .host(swaggerHost)
                .securitySchemes(singletonList(new BasicAuth("basicAuth")))
                .securityContexts(singletonList(securityContext()));
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(singletonList(basicAuthReference()))
                .operationSelector(operationContext -> true)
                .build();
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }

}
