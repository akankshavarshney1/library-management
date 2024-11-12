package com.example.library_management.dto.Object;

import com.example.library_management.utils.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserObject {

    private Long id;

    @NotNull(message = "Username cannot be null. Please provide a valid username.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    private String username;

    @NotNull(message = "Email cannot be null. Please specify a valid Email.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotNull(message = "Role cannot be null. Please specify a valid role.")
    private String role;

    @NotNull(message = "Password cannot be null. Please provide a password.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    private boolean enabled;

    private LocalDateTime createdAt;
}
