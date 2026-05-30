package com.booknest.controllers;

import com.booknest.model.BookResponse;
import com.booknest.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponse> createBook(

            @RequestParam String isbn, @RequestParam String title, @RequestParam String author, @RequestParam(required = false) String publisher, @RequestParam(required = false) String category, @RequestParam(required = false) String description, @RequestParam BigDecimal price, @RequestParam MultipartFile image

    ) {

        BookResponse response = bookService.createBook(isbn, title, author, publisher, category, description, price, image);

        return ResponseEntity.ok(response);
    }
}