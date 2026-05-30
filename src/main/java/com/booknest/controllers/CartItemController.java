package com.booknest.controllers;

import com.booknest.entities.CartItem;
import com.booknest.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @RequestParam Long cartId,
            @RequestParam Long bookId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                cartItemService.addToCart(
                        cartId,
                        bookId,
                        quantity
                )
        );
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItems(
            @PathVariable Long cartId) {

        return ResponseEntity.ok(
                cartItemService.getCartItems(cartId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCartItem(
            @PathVariable Long id) {

        cartItemService.removeCartItem(id);

        return ResponseEntity.ok(
                "Item removed from cart"
        );
    }
}