package com.example.library_management.controller;

import com.example.library_management.entities.Book;
import com.example.library_management.dto.Object.BookObject;
import com.example.library_management.services.BookService;
import com.example.library_management.dto.Response.Response;
import com.example.library_management.dto.Response.GenericResponse;
import com.example.library_management.utils.Constants.CustomStatusCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    private final BookService bookService;
    private final GenericResponse<Book> response = new GenericResponse<>();

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Response<List<Book>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        GenericResponse<List<Book>> response = new GenericResponse<>();
        return ResponseEntity.ok(response.createSuccessResponse(books, "Books retrieved successfully.", CustomStatusCode.BOOKS_RETRIEVED_SUCCESSFULLY));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Book>> getBookById(@PathVariable Long id) {
        Optional<Book> optionalBook = bookService.getBookById(id);
        if (optionalBook.isPresent()) {
            return ResponseEntity.ok(response.createSuccessResponse(optionalBook.get(), "Book retrieved successfully.", CustomStatusCode.BOOK_RETRIEVED_SUCCESSFULLY));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Response<Book>> createBook(@Valid @RequestBody BookObject bookDto) {
        Book newBook = bookService.createBook(bookDto);
        return ResponseEntity.ok(response.createSuccessResponse(newBook, "Book created successfully.", CustomStatusCode.BOOK_CREATED_SUCCESSFULLY));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<Book>> updateBook(@PathVariable Long id, @RequestBody BookObject bookDto) {
        Book updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(response.createSuccessResponse(updatedBook, "Book updated successfully.", CustomStatusCode.BOOK_UPDATED_SUCCESSFULLY));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<Book>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(response.createSuccessResponse(null, "Book deleted successfully.", CustomStatusCode.BOOK_DELETED_SUCCESSFULLY));
    }
}
