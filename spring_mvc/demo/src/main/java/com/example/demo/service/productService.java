package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class productService {
    public String fetchedProduct() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Fetching Product from Database:");
        return "Product list loaded";
    }
}
