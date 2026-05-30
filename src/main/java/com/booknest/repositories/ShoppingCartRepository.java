package com.booknest.repositories;

import com.booknest.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository
        extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByCustomerId(Long customerId);
}