//this class is basically used for enforcing rule 1)age validation 2) email shouldnot be null or missing
package com.example.firstspringbootex;

public class UserValidation {
    // edge case:email mustnot be missing or null
    public boolean isValid(Users user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return false;
        }

        // if age is <=18 return false
        if (user.getAge() < 18) {
            return false;
        }

        // emial basic regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return user.getEmail().matches(emailRegex);
    }
}
