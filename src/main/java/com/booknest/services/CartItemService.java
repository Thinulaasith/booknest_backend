package com.booknest.services;

import com.booknest.entities.Book;
import com.booknest.entities.CartItem;
import com.booknest.entities.ShoppingCart;
import com.booknest.repositories.BookRepository;
import com.booknest.repositories.CartItemRepository;
import com.booknest.repositories.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;

    public CartItem addToCart(
            Long cartId,
            Long bookId,
            Integer quantity) {

        ShoppingCart cart = shoppingCartRepository
                .findById(cartId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));

        CartItem existing = cartItemRepository
                .findByShoppingCartIdAndBookId(
                        cartId,
                        bookId)
                .orElse(null);

        if (existing != null) {

            existing.setQuantity(
                    existing.getQuantity() + quantity
            );

            return cartItemRepository.save(existing);
        }

        CartItem item = CartItem.builder()
                .shoppingCart(cart)
                .book(book)
                .quantity(quantity)
                .build();

        return cartItemRepository.save(item);
    }

    public List<CartItem> getCartItems(Long cartId) {
        return cartItemRepository.findByShoppingCartId(cartId);
    }

    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}