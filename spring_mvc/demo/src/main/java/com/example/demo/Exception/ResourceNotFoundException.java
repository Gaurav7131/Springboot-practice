package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// tells the spring mvc to mapped this exception directly to HTTP 404(not found) by extending RunTimeExcepion class
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    // constructor
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
