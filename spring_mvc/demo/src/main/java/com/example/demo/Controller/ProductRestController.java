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

    // GET /api/v1/products - Fetch All
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(new ArrayList<>(store.values()));
    }

    // GET /api/v1/products/{id} - Fetch One
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = store.get(id);
        return (product != null)
                ? ResponseEntity.ok(product)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // POST /api/v1/products - Create Resource
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product input) {
        Long newId = idGenerator.getAndIncrement();
        Product created = new Product(newId, input.name(), input.price());
        store.put(newId, created);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/v1/products/{id} - Full Update / Replace
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product input) {
        if (!store.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Product updated = new Product(id, input.name(), input.price());
        store.put(id, updated);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/v1/products/{id} - Remove Resource
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return (store.remove(id) != null)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
