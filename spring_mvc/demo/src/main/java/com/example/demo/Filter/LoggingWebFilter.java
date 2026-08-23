package com.example.demo.Filter;

import java.io.IOException;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class LoggingWebFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpreq = (HttpServletRequest) request;

        long startTime = System.currentTimeMillis();
        System.out.println("Request Url Loading:" + httpreq.getRequestURI());

        // Pass the request along the chain to the next filter/servlet
        chain.doFilter(request, response);
        long processingTime = System.currentTimeMillis() - startTime;
        System.out.println("Requested Url finished:" + httpreq.getRequestURI() + "Duration" + processingTime + "ms.");

    }

}
