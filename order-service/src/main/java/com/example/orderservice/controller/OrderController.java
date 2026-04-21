package com.example.orderservice.controller;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Order order) {
        try {
            return ResponseEntity.ok(service.createOrder(order));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @GetMapping
    public List<Order> getAll() {
        return service.getAllOrders();
    }
}
