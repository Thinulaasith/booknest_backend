package com.booknest.services;

import com.booknest.entities.Customer;
import com.booknest.entities.ShoppingCart;
import com.booknest.repositories.CustomerRepository;
import com.booknest.repositories.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CustomerRepository customerRepository;

    public ShoppingCart createCart(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        if (shoppingCartRepository
                .findByCustomerId(customerId)
                .isPresent()) {

            throw new RuntimeException(
                    "Customer already has a cart"
            );
        }

        ShoppingCart cart = ShoppingCart.builder()
                .customer(customer)
                .build();

        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart getCartByCustomer(Long customerId) {

        return shoppingCartRepository
                .findByCustomerId(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));
    }
}