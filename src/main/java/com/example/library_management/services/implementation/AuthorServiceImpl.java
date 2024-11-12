package com.example.library_management.services.implementation;

import com.example.library_management.dto.Object.AuthorObject;
import com.example.library_management.entities.Author;
import com.example.library_management.repositories.AuthorRepository;
import com.example.library_management.services.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AuthorObject> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> modelMapper.map(author, AuthorObject.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorObject> getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(value -> modelMapper.map(value, AuthorObject.class));
    }

    @Override
    public AuthorObject createAuthor(AuthorObject authorObject) {
        Author author = modelMapper.map(authorObject, Author.class);
        Author savedAuthor = authorRepository.save(author);
        return modelMapper.map(savedAuthor, AuthorObject.class);
    }

    @Override
    public AuthorObject updateAuthor(Long id, AuthorObject authorObject) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));



        modelMapper.map(authorObject, author);
        author.setId(id);

        Author updatedAuthor = authorRepository.save(author);

        return modelMapper.map(updatedAuthor, AuthorObject.class);
    }


    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
