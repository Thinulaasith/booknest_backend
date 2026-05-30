package com.booknest.services;

import com.booknest.entities.Book;
import com.booknest.model.BookResponse;
import com.booknest.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private static final String UPLOAD_DIR = "uploads/books/";

    public BookResponse createBook(
            String isbn,
            String title,
            String author,
            String publisher,
            String category,
            String description,
            BigDecimal price,
            MultipartFile image
    ) {

        if (bookRepository.findByIsbn(isbn).isPresent()) {
            throw new RuntimeException("ISBN already exists");
        }

        String imageName = saveImage(image);

        Book book = Book.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .category(category)
                .description(description)
                .price(price)
                .imageUrl(imageName)
                .active(true)
                .build();

        book = bookRepository.save(book);

        return BookResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .category(book.getCategory())
                .description(book.getDescription())
                .imageUrl(book.getImageUrl())
                .price(book.getPrice())
                .active(book.isActive())
                .build();
    }

    private String saveImage(MultipartFile image) {

        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName =
                    image.getOriginalFilename();

            String extension = "";

            if (originalFileName != null &&
                    originalFileName.contains(".")) {

                extension = originalFileName.substring(
                        originalFileName.lastIndexOf(".")
                );
            }

            String uuidFileName =
                    UUID.randomUUID() + extension;

            Path filePath =
                    uploadPath.resolve(uuidFileName);

            Files.copy(
                    image.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return uuidFileName;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload image",
                    e
            );
        }
    }
}