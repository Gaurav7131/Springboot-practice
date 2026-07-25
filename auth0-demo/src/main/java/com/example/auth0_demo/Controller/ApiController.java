package com.example.auth0_demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api/private")
    public String privateEndPoint() {
        return "Success! You have a valid Auth0 token and accessed the private API!";
    }
}
