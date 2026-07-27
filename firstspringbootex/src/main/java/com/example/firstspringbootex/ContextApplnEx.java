package com.example.firstspringbootex;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ContextApplnEx implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // u can actually tweak or inspect the env before spring boot loads or bean st
        System.out.println("Setting up system properties");
        applicationContext.getEnvironment().getSystemProperties().put("My custom propeties", "Active");

    }

}
