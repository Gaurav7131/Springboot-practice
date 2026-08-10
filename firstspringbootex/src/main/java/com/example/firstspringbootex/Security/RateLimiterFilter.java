package com.example.firstspringbootex.Security;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {

    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Request knocking on the door of the server(getRemoteAddr())
        String clientIp = request.getRemoteAddr();

        // Get current count, default to 0 if new user
        int currentCount = requestCounts.getOrDefault(clientIp, 0);

        // Limiting rule: if requests exceed 5/min, block
        if (currentCount >= 5) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Sorry to say but U exceeded limit .Too many requests! Blocked.");
            return;
        }

        // Increment count and store back
        requestCounts.put(clientIp, currentCount + 1);

        // Allow request to proceed to the controller
        filterChain.doFilter(request, response);
    }
}