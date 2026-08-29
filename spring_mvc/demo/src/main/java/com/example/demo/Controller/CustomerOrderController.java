package com.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

record CustomerOrder(Long id, String orderNumber, String status) {
}

@RestController
@RequestMapping("/api/v1/customer-orders") // Plural noun, kebab-case, versioned, no trailing slash
public class CustomerOrderController {

    private final Map<Long, CustomerOrder> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // GET /api/v1/customer-orders?status=active -> Filter via Query Param
    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getOrders(
            @RequestParam(name = "status", required = false) String status) {

        List<CustomerOrder> results = store.values().stream()
                .filter(order -> status == null || order.status().equalsIgnoreCase(status))
                .toList();
        return ResponseEntity.ok(results);
    }

    // GET /api/v1/customer-orders/{id} -> Specific Resource
    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Long id) {
        CustomerOrder order = store.get(id);
        return (order != null)
                ? ResponseEntity.ok(order)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // POST /api/v1/customer-orders -> Create Resource
    @PostMapping
    public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrder input) {
        Long newId = idGenerator.getAndIncrement();
        CustomerOrder order = new CustomerOrder(newId, input.orderNumber(), input.status());
        store.put(newId, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // DELETE /api/v1/customer-orders/{id} -> Delete Resource
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return (store.remove(id) != null)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}