package com.example.library_management.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;
}