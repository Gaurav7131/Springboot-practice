package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//want all product attributes at 1 pace:record
record Product(Long id, String name, Double price) {
}

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {
    private final Map<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // GET-fetch all products
    @GetMapping
    private ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(new ArrayList<>(store.values()));
    }

    // GET- fetch one product specific
    @GetMapping("/{id}")
    private ResponseEntity<Product> getProductByid(@PathVariable Long id) {
        Product product = store.get(id);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    private ResponseEntity<Product> newProduct(@RequestBody Product p) {
        Long newId = idGenerator.incrementAndGet();
        Product createdProduct = new Product(newId, p.name(), p.price());
        store.put(newId, createdProduct);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createdProduct);

    }

    // Put-Full Update or Replace
    @PutMapping("/{id}")
    private ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product p) {
        if (!store.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Replace Product
        Product updatedProduct = new Product(id, p.name(), p.price());
        store.put(id, updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete
    @DeleteMapping("/{id}")
    private ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        return (store.remove(id) != null) ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

}
