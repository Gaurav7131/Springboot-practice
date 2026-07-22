package com.example.firstspringbootex;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTokenStore {

    // Maps the String Token -> to the Long User ID
    private final Map<String, Long> tokenStore = new ConcurrentHashMap<>();

    // Requirement: issue random UUID token
    public String issueToken(Long userId) {
        // 1. Generate a secure, random string (The Session Pass)
        String token = UUID.randomUUID().toString();

        // 2. Save it in our map, linking the token to the specific user
        tokenStore.put(token, userId);

        return token;
    }

    // Requirement: resolveUserId
    public Optional<Long> resolveUserId(String token) {
        // Look up the token in the map. If it exists, return the User ID.
        return Optional.ofNullable(tokenStore.get(token));
    }

    // Requirement: revoke
    public void revoke(String token) {
        // When the user logs out, we throw their token in the trash
        tokenStore.remove(token);
    }
}