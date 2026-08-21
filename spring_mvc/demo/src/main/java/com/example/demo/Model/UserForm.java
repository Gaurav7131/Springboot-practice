package com.example.demo.Model;

import jakarta.validation.constraints.*;

public class UserForm {
    @NotBlank(message = "Username must not be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 to 20 character")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please provide valid email address")
    private String email;

    @NotNull(message = "Age can't be null")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 99, message = "Age must be under 100")
    private Integer age;

    // Getter & Setter method
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
