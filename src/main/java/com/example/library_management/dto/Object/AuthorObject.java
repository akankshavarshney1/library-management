package com.example.library_management.dto.Object;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorObject {

    private Long id;

    @NotNull(message = "First name cannot be null.")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters.")
    private String firstName;

    @NotNull(message = "Last name cannot be null.")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters.")
    private String lastName;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotNull(message = "Birth date cannot be null.")
    @Past(message = "Birth date must be a past date.")
    private LocalDate birthDate;
}
