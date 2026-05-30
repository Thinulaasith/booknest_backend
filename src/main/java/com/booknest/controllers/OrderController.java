package com.booknest.controllers;

import com.booknest.entities.Order;
import com.booknest.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestParam Long customerId,
            @RequestParam BigDecimal totalAmount) {

        return ResponseEntity.ok(
                orderService.createOrder(
                        customerId,
                        totalAmount
                )
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrders(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                orderService.getCustomerOrders(
                        customerId
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.getOrder(id)
        );
    }
}