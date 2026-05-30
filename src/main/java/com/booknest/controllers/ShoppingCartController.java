package com.booknest.controllers;

import com.booknest.entities.ShoppingCart;
import com.booknest.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/create/{customerId}")
    public ResponseEntity<ShoppingCart> createCart(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                shoppingCartService.createCart(customerId)
        );
    }
}