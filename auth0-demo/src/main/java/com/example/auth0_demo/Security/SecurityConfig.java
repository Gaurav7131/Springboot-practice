package com.example.auth0_demo.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {

    // Grab the custom variables from application.properties
    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.
        // tell the Bouncer(SecurityConfig) to protect all our endpts
                authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                // tell the bouncer to look for our jwt in the header
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));
        // << tell the spring that use our custom decoder

        return http.build();
    }

    // custom decoder that decodes & checks the audience
    @Bean
    // decoder that checks the auth0 signature and issuer
    public JwtDecoder jwtDecoder() {

        // add custom http rest template to resolve issue of timeout
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(15000);// 15 secs to connect
        requestFactory.setReadTimeout(15000);// 15 sec to download & reading keys
        restTemplate.setRequestFactory(requestFactory);

        // build decoder using patient http client
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(issuer).restOperations(restTemplate).build();

        // custom rule:audience must be in specified api
        OAuth2TokenValidator<Jwt> audienceValidator = jwt -> {
            if (jwt.getAudience().contains(audience)) {
                return OAuth2TokenValidatorResult.success();
            }
            OAuth2Error oAuth2Error = new OAuth2Error("invalid token", "Token is missing", null);
            return OAuth2TokenValidatorResult.failure(oAuth2Error);
        };

        // Combine the default issuer check with our new audience check
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }
}
