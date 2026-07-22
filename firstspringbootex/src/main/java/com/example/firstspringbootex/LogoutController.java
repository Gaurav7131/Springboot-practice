package com.example.firstspringbootex;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    private final InMemoryTokenStore tokenStore;

    public LogoutController(InMemoryTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    // 3. POST /logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Step 1: Extract the token if it exists
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Step 2: Revoke it.
            // Because our tokenStore uses a Map, map.remove() is naturally idempotent!
            // If the token is already gone, it just does nothing and doesn't crash.
            tokenStore.revoke(token);
        }

        //
        return ResponseEntity.noContent().build();
    }
}
