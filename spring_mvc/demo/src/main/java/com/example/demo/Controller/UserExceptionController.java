package com.example.demo.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserExceptionController {

    private static final Map<Long, String> USER_DB = new HashMap<>();

    static {
        USER_DB.put(1L, "Gaurav");
        USER_DB.put(2L, "Thakare");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        // 1. Check for invalid negative/zero ID
        if (id <= 0) {
            throw new IllegalArgumentException("User ID must be greater than zero.");
        }

        // 2. Check if the user exists in the database
        if (!USER_DB.containsKey(id)) {
            // Throws ResourceNotFoundException (handled by GlobalExceptionHandler or
            // returns 404)
            throw new ResourceNotFoundException("User with ID: " + id + " doesn't exist");
        }

        // 3. Success response
        return ResponseEntity.ok("User Found: " + USER_DB.get(id));
    }

    // Controller-specific exception handling for IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("Controller-Level: " + ex.getMessage());
    }
}