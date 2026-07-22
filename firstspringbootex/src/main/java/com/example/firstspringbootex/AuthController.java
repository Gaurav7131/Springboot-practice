package com.example.firstspringbootex;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// Simple records to handle incoming and outgoing JSON data
record LoginRequest(String email, String password) {
}

record TokenResponse(String token) {
}

@RestController
public class AuthController {

    private final InMemoryUserStore userStore;
    private final InMemoryTokenStore tokenStore;

    // Constructor Injection
    public AuthController(InMemoryUserStore userStore, InMemoryTokenStore tokenStore) {
        this.userStore = userStore;
        this.tokenStore = tokenStore;
    }

    // 1. POST /login
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        // Step 1: Find the user by email
        Optional<User> optionalUser = userStore.findByEmail(request.email());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Step 2: Hash the provided password and compare it with the stored hash
            String hashedInputPassword = PasswordHasher.hashPassword(request.password());

            if (user.passwordHash().equals(hashedInputPassword)) {
                // Step 3: Valid credentials! Issue a token.
                String token = tokenStore.issueToken(user.id());
                return ResponseEntity.ok(new TokenResponse(token)); // Returns 200 OK
            }
        }

        // Step 4: Failure! Return 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
