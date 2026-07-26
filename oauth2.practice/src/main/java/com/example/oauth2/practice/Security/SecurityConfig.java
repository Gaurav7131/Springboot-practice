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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Best practice: mark injected dependencies as 'final'
    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                authz -> authz.requestMatchers("/", "/public").permitAll().anyRequest().authenticated())
                // Enables the OIDC Client Flow (Redirects to IdP for login)
                .oauth2Login(Customizer.withDefaults())
                // Add the logout configuration
                .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()));

        return http.build();
    }

    /*
     * OIDC handler that terminates the IdP's global session and redirects to
     * the IdP's logout endpoints. Because global logout is a two-step process:
     * 1) clear the Spring session,
     * 2) clear the user's browser session with the IdP.
     * If the user logs in again without this, they will bypass the password screen.
     */
    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        // remember : Capital 'O'
        // Tells Spring to reach out to the IdP and trigger the global logout
        OidcClientInitiatedLogoutSuccessHandler logoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(
                this.clientRegistrationRepository);

        /*
         * remember Capital 'U' in {baseUrl} resolves redirect baseurl
         * (ex:http://localhost:8080)
         * Where the user should be redirected AFTER the IdP logs them out
         */
        logoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/");

        return logoutSuccessHandler;
    }

}
