package com.example.firstspringbootex;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

// A safe record that EXCLUDES the password hash
record SafeUserResponse(Long id, String email) {
}

@RestController
public class ProfileController {

    private final InMemoryUserStore userStore;
    private final InMemoryTokenStore tokenStore;

    public ProfileController(InMemoryUserStore userStore, InMemoryTokenStore tokenStore) {
        this.userStore = userStore;
        this.tokenStore = tokenStore;
    }

    // 2. GET /profile
    @GetMapping("/profile")
    public ResponseEntity<SafeUserResponse> getProfile(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        // Step 1: Check if the header is missing or incorrectly formatted
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Step 2: Extract the exact token string
        String token = authHeader.substring(7); // Removes "Bearer " (7 characters)

        // Step 3: Try to find a valid User ID for this token
        Optional<Long> userIdOpt = tokenStore.resolveUserId(token);

        if (userIdOpt.isPresent()) {
            // Step 4: Token is valid! Fetch the user details using the ID
            // Assuming findById exists in your InMemoryUserStore
            Optional<User> userOpt = userStore.findById(userIdOpt.get());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // Return 200 OK with SAFE data
                return ResponseEntity.ok(new SafeUserResponse(user.id(), user.email()));
            }
        }

        // Step 5: Invalid token or user deleted! Return 401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
