package com.example.oauth2.practice.Controller;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api/public")
    public String publicApi() {
        return "This is the public APi Anyone can read this...";
    }

    // Protected page(needs Login) /private
    @GetMapping("/api/private")
    public String privateApi(@AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getSubject();
        return "Accecs granted! Jwt validated Successfully Our suject id is : " + subject;
    }
}
