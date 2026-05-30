package com.booknest.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookResponse {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String category;
    private String imageUrl;
    private String description;
    private BigDecimal price;
    private boolean active;
}