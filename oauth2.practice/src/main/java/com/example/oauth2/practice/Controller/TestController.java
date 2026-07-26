package com.example.oauth2.practice.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // Door 1:Public Page (Accesiible to anyone due to
    // we have requestMatcher("/").permitAll().anyrequest().authencitated())
    @GetMapping("/")
    public String Home() {
        return "Hello this is our Home Page:";
    }

    // Door 2:Private page(Acessible to Authorized users only req.logins)
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal OidcUser user) {
        return "This is priVate requires login:" + user.getFullName() + "&" + user.getEmail();
    }
}
