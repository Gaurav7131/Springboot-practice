package com.example.oauth2.practice.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

//Purpose of this class instead of using oauth2Login used oauth2ResourceServer & Spring boot acts as APi ,validate jwt
@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

    // dependancy needed for global logout to terminate all sessions
    public ClientRegistrationRepository clientRegistrationRepository;

    // constructor injection
    public ResourceServerConfig(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.authorizeHttpRequests(
                authz -> authz.requestMatchers("/", "/public", "/api/public").permitAll().anyRequest().authenticated())
                // oidc client flow redirect to idp for login
                .oauth2Login(Customizer.withDefaults())
                // enable global logout
                .logout(logout -> logout.logoutSuccessHandler(oidcSucccessHandler()))
                // Tells Spring Boot to act as an API and validate incoming JWTs
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    // handler of oidc tells the idp and trigger the global endpt
    private LogoutSuccessHandler oidcSucccessHandler() {

        OidcClientInitiatedLogoutSuccessHandler logoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(
                this.clientRegistrationRepository);

        // resolve redirect url issue defaults to http://localhost:8080
        logoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/");

        return logoutSuccessHandler;
    }
}
