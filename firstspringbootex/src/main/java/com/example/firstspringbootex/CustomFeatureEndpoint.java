package com.example.firstspringbootex;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "featureToggle")
public class CustomFeatureEndpoint {

    private Map<String, Boolean> features = new HashMap<>();

    public CustomFeatureEndpoint() {
        // Adding some default data for demonstration
        features.put("newUI", true);
        features.put("betaTesting", false);
    }

    // Maps to HTTP GET request
    @ReadOperation
    public Map<String, Boolean> getAllFeatures() {
        return features;
    }

    // Maps to HTTP POST request
    @WriteOperation
    public void updateFeature(String featureName, boolean isEnabled) {
        features.put(featureName, isEnabled);
    }

    // Maps to HTTP DELETE request
    @DeleteOperation
    public void deleteFeature(String featureName) {
        features.remove(featureName);
    }
}
/*
 * What is a Denial of Service (DoS) Attack?
 * A Denial of Service (DoS) attack is a malicious attempt to make a computer,
 * network, or application resource unavailable to its intended users. This is
 * typically achieved by flooding the target server with an overwhelming volume
 * of requests or traffic, exhausting its system resources (such as CPU, memory,
 * network bandwidth, or database connection pools).
 * 
 * When these resources are completely depleted, the server can no longer
 * respond to legitimate user requests, effectively crashing or hanging.
 * 
 * Distributed Denial of Service (DDoS): A variation of a DoS attack where the
 * malicious traffic comes from multiple distributed sources (often a botnet
 * spread globally), making it much harder to block by simply blacklisting a
 * single IP address.
 * 
 * Brute Force Attack: A related concept where an attacker repeatedly attempts
 * thousands or millions of combinations of usernames and passwords to guess
 * valid credentials. While it doesn't always crash the server via sheer traffic
 * volume, it consumes CPU and database cycles, and if successful, leads to
 * unauthorized access.
 * 
 * Why Spring Security Doesn't Protect Against DoS and Brute Force by Default
 * Spring Security is an exceptionally powerful framework, but it does not
 * protect against DoS attacks or brute force attempts out-of-the-box for
 * several architectural and design reasons:
 * 
 * 1. Scope of Spring Security (Authentication vs. Infrastructure)
 * Spring Security's primary mandate is Authentication (verifying who a user is)
 * and Authorization (determining what a user is allowed to do). It operates at
 * the application layer as a series of Servlet filters to inspect credentials,
 * tokens, and roles.
 * 
 * It is not designed to act as a network traffic filter, web application
 * firewall (WAF), or infrastructure-level load balancer.
 * 
 * Preventing a volumetric DoS attack requires controlling traffic before it
 * even hits the application server or Java runtime (e.g., at the cloud
 * provider, API gateway, or reverse proxy level like Nginx/Cloudflare).
 * 
 * 2. Statelessness and Resource Blindness
 * By default, standard stateless authentication mechanisms (like JWT or HTTP
 * Basic) evaluate each incoming request independently. Spring Security does not
 * track historical request counts per IP address, client fingerprint, or user
 * account across time windows.
 * 
 * Without tracking state (e.g., storing how many failed login attempts occurred
 * within the last minute), the framework has no way of knowing whether a flurry
 * of requests is a brute-force attack or simply a high volume of legitimate
 * traffic from a busy corporate network.
 * 
 * 3. Separation of Concerns (Infrastructure vs. Application Layer)
 * Enterprise architecture separates security responsibilities:
 * 
 * Infrastructure Layer: Cloud providers (AWS Shield, Cloudflare), API Gateways
 * (Spring Cloud Gateway, Kong), and Reverse Proxies (Nginx, HAProxy) handle
 * rate limiting, IP blacklisting, traffic scrubbing, and DDoS mitigation.
 * 
 * Application Layer: Spring Security handles method-level security, password
 * encoding, CORS, CSRF, and JWT validation.
 * 
 * Forcing Spring Security to manage low-level network traffic throttling would
 * degrade application performance, as the JVM would waste precious CPU cycles
 * processing requests just to drop them.
 * 
 * How to Implement Protection in Spring Boot
 * To protect your Spring Boot application against DoS, brute-force attacks, and
 * credential stuffing, you must implement specific controls:
 * 
 * Rate Limiting (API Gateway / Bucket4j): Restrict the number of requests a
 * single IP address or user can make within a specified time frame. This can be
 * done using libraries like Bucket4j inside Spring Boot or at an API Gateway
 * (like Spring Cloud Gateway).
 * 
 * Account Lockout Policies: Track failed login attempts in your database or
 * Redis cache. If a user (or IP) exceeds 5 consecutive failed password
 * attempts, temporarily lock the account or block the IP for 15 minutes.
 * 
 */