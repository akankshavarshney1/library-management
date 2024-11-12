package com.example.library_management.controller;

import com.example.library_management.dto.Object.AuthorObject;
import com.example.library_management.dto.Response.Response;
import com.example.library_management.dto.Response.GenericResponse;
import com.example.library_management.services.AuthorService;
import com.example.library_management.utils.Constants.CustomStatusCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
@Validated
public class AuthorController {

    private final AuthorService authorService;
    private final GenericResponse<AuthorObject> response = new GenericResponse<>();

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Response<List<AuthorObject>>> getAllAuthors() {
        List<AuthorObject> authors = authorService.getAllAuthors();
        GenericResponse<List<AuthorObject>> response = new GenericResponse<>();
        return ResponseEntity.ok(response.createSuccessResponse(authors, "Authors retrieved successfully.", CustomStatusCode.AUTHORS_RETRIEVED_SUCCESSFULLY));
    }



    @GetMapping("/{id}")
    public ResponseEntity<Response<AuthorObject>> getAuthorById(@PathVariable Long id) {
        Optional<AuthorObject> authorOptional = authorService.getAuthorById(id);
        if (authorOptional.isPresent()) {
            return ResponseEntity.ok(response.createSuccessResponse(authorOptional.get(), "Author retrieved successfully.", CustomStatusCode.AUTHOR_RETRIEVED_SUCCESSFULLY));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Response<AuthorObject>> createAuthor(@Valid @RequestBody AuthorObject authorDto) {
        AuthorObject newAuthor = authorService.createAuthor(authorDto);
        return ResponseEntity
                .created(URI.create("/api/authors/" + newAuthor.getId()))
                .body(response.createSuccessResponse(newAuthor, "Author created successfully.", CustomStatusCode.AUTHOR_CREATED_SUCCESSFULLY));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<AuthorObject>> updateAuthor(@PathVariable Long id, @RequestBody AuthorObject authorDto) {
        AuthorObject updatedAuthor = authorService.updateAuthor(id, authorDto);
        return ResponseEntity.ok(response.createSuccessResponse(updatedAuthor, "Author updated successfully.", CustomStatusCode.AUTHOR_UPDATED_SUCCESSFULLY));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<AuthorObject>> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok(response.createSuccessResponse(null, "Author deleted successfully.", CustomStatusCode.AUTHOR_DELETED_SUCCESSFULLY));
    }
}
