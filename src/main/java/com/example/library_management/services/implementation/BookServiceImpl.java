package com.example.library_management.services.implementation;

import com.example.library_management.dto.Object.BookObject;
import com.example.library_management.entities.Book;
import com.example.library_management.repositories.BookRepository;
import com.example.library_management.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book createBook(BookObject bookObject) {
        Book book = new Book();
        book.setTitle(bookObject.getTitle());
        book.setAuthorId(bookObject.getAuthorId());
        book.setIsbn(bookObject.getIsbn());
        book.setPublishedDate(bookObject.getPublishedDate());
        book.setPrice(bookObject.getPrice());
        book.setCreatedBy("ADMIN");
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, BookObject bookObject) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(bookObject.getTitle());
        book.setIsbn(bookObject.getIsbn());
        book.setPublishedDate(bookObject.getPublishedDate());
        book.setPrice(bookObject.getPrice());
        book.setLastModifiedBy(bookObject.getCreatedBy());

        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);
    }
}
