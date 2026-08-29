package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc // default to spring mvc infrastructure
public class WebMvcConfig implements WebMvcConfigurer {
    // ResourceHttpRequestHandler -> ResourceHandlerRegistry(config file that maps
    // urlpattern-physical file loc)
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**", "/uploads/**", "/favicon.ico")
                // Serving static res.to multiple loc.
                .addResourceLocations("classpath:/static/", // inside WAR/JAR
                        "classpath:/public/",
                        "file:///D:/uploads/", // external file system uploads(outside WAR/JAR)
                        "file:/var/www/external-images/")
                .setCachePeriod(3600)// cache for 1 hr
                .resourceChain(true)// enable ResourceResolver chaining
                .addResolver(new EncodedResourceResolver())// serving to compressed-file(.gzip,br)
                .addResolver(new PathResourceResolver());// fallback to std path,serving to std uncompressed
    }

}
