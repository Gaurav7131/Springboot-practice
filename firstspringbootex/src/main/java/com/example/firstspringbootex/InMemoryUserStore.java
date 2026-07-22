package com.example.firstspringbootex;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// A simple User record to hold the data
record User(Long id, String email, String passwordHash) {
}

public class InMemoryUserStore {

    // Two maps for fast lookups (One by ID, One by Email)
    private final Map<Long, User> usersById = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();

    // Auto-incrementing ID generator
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Requirement: saveNew
    public User saveNew(String email, String rawPassword) {
        // Check if email already exists
        if (usersByEmail.containsKey(email)) {
            throw new IllegalArgumentException("User with this email already exists!");
        }

        // Generate new ID and hash the password
        Long newId = idGenerator.getAndIncrement();
        String hashedPassword = PasswordHasher.hashPassword(rawPassword); // Using our Hasher from Step 1

        // Create user and save in both maps
        User newUser = new User(newId, email, hashedPassword);
        usersById.put(newId, newUser);
        usersByEmail.put(email, newUser);

        return newUser;
    }

    // requiremet:findbyId
    public Optional<User> findById(Long id) {
        // Returns the user if found, or an empty Optional if not
        return Optional.empty();
    }

    // Requirement: findByEmail
    public Optional<User> findByEmail(String email) {
        // Returns the user if found, or an empty Optional if not
        return Optional.ofNullable(usersByEmail.get(email));
    }
}