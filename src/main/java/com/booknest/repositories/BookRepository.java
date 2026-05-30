package com.booknest.repositories;

import com.booknest.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    List<Book> findByActiveTrue();

    List<Book> findByCategoryAndActiveTrue(String category);

    List<Book> findByTitleContainingIgnoreCaseAndActiveTrue(String keyword);

    Optional<Book> findByIdAndActiveTrue(Long id);
}