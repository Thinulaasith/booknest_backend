package com.booknest.repositories;

import com.booknest.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {

    List<CartItem> findByShoppingCartId(Long shoppingCartId);

    Optional<CartItem> findByShoppingCartIdAndBookId(
            Long shoppingCartId,
            Long bookId
    );
}