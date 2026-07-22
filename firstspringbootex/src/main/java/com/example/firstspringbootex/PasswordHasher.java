package com.example.firstspringbootex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    // this method makes a raw password and SHA-256
    public static String hashPassword(String rawPassword) {
        try {
            // implememt SHA-256 hash algo
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // convert the password into bytes and hashed it
            byte[] encodedhash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));

            // convert the bytes into string of readable format
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');// padding if needed
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorith not found!" + e.getMessage());
        }

    }
}
