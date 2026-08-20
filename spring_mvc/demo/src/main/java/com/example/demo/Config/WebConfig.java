package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc // switches to default spring mvc infra(config_handler,type_converter)
@ComponentScan(basePackages = "com.example.demo.controller") // scanning component
public class WebConfig {
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver irvr = new InternalResourceViewResolver();
        irvr.setPrefix("/WEB-INF/views/");// folder where JSP/HTML pages live
        irvr.setSuffix(".jsp");// extensions
        return irvr;
    }

}
