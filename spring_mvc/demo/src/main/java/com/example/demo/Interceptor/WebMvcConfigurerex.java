package com.example.demo.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.demo.Filter.ExehandlerInterceptor;

@Configuration
public class WebMvcConfigurerex implements WebMvcConfigurer {

    private final ExehandlerInterceptor executionTimeInterceptor;

    // Constructor Injection
    public WebMvcConfigurerex(ExehandlerInterceptor executionTimeInterceptor) {
        this.executionTimeInterceptor = executionTimeInterceptor;
    }

    // Overrides WebMvcConfigurer method
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(executionTimeInterceptor)
                .addPathPatterns("/api/**");
    }
}