package com.example.firstspringbootex.Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    private final RateLimiterFilter rateLimiterFilter;

    // constructor
    public SecurityConfig(RateLimiterFilter rateLimiterFilter) {
        this.rateLimiterFilter = rateLimiterFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disables CSRF using the modern Spring Security 6+ method reference
                .csrf(AbstractHttpConfigurer::disable)

                // Add our custom rate limiter filter right before the standard authentication
                .addFilterBefore(rateLimiterFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
