package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
// loads multiple custom properties files into spring's env
@PropertySources({ @PropertySource("classpath:custom_app.properties") })
public class CustomAppConfig {
}
