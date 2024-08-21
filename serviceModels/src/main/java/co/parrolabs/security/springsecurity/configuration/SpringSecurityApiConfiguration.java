package co.parrolabs.security.springsecurity.configuration;


import co.parrolabs.security.CustomAuthenticationEntryPoint;
import co.parrolabs.security.filter.OktaBasicAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static co.parrolabs.controller.constants.ControllerConstants.API_ROOT;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityApiConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private OktaBasicAuthenticationFilter oktaBasicAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .addFilterBefore(oktaBasicAuthenticationFilter, BasicAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/error/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/customer/**")
                .antMatchers("/product/**")
                .antMatchers(API_ROOT.concat("/**"))
                .antMatchers("/actuator/health/**")
                .antMatchers("/v2/api-docs"
                        , "/configuration/ui"
                        , "/swagger-resources/**"
                        , "/configuration/**"
                        , "/swagger-ui.html"
                        , "/webjars/**");
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
}