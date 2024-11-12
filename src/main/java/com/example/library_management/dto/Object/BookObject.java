package com.example.library_management.dto.Object;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookObject {

    private Long id;

    @NotNull(message = "Title cannot be null.")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters.")
    private String title;

    @NotNull(message = "ISBN cannot be null.")
    @Column(unique = true, nullable = false)
    private String isbn;

    @NotNull(message = "Published date cannot be null.")
    @PastOrPresent(message = "Published date cannot be in the future.")
    private LocalDate publishedDate;

    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price must be a positive value.")
    private BigDecimal price;

    @NotNull(message = "Author ID cannot be null.")
    private Long authorId;

    private String createdBy;
    private String lastModifiedBy;
}
