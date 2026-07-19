package com.example.firstspringbootex;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

//Tells the springboot that we are using restapi on this class
@RestController
public class User {

    // Maps the HTTP request to this specific method
    @GetMapping("/api/greet")
    public String SayHello() {
        return "Hii SpringBoot REST , Welcome back  g-AURA-v";

    }

}
