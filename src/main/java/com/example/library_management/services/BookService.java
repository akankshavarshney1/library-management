package com.example.library_management.services;

import com.example.library_management.dto.Object.BookObject;
import com.example.library_management.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Book createBook(BookObject bookObject);

    Book updateBook(Long id, BookObject bookObject);

    void deleteBook(Long id);
}
