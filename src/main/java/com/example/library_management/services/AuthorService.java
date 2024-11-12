package com.example.library_management.services;

import com.example.library_management.dto.Object.AuthorObject;
import com.example.library_management.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorObject> getAllAuthors();

    Optional<AuthorObject> getAuthorById(Long id);

    AuthorObject createAuthor(AuthorObject authorObject);

    AuthorObject updateAuthor(Long id, AuthorObject authorObject);

    void deleteAuthor(Long id);
}
