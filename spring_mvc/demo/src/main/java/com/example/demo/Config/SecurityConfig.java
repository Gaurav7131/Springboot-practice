package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    @Bean
    public org.springframework.security.web.SecurityFilterChain SecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.authorizeHttpRequests(
                // Allow unauthenticated access to static resources
                auth -> auth.requestMatchers("/resources/**", "/uploads/**", "/favicon.ico").permitAll().anyRequest()
                        .authenticated())
                .formLogin(form -> form.permitAll());

        return http.build();
    }

}
