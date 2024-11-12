package com.example.library_management.controller;

import com.example.library_management.dto.Object.UserObject;
import com.example.library_management.services.AuthService;
import com.example.library_management.dto.Response.Response;
import com.example.library_management.dto.Response.GenericResponse;
import com.example.library_management.utils.Constants.CustomStatusCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;
    private final GenericResponse<UserObject> response = new GenericResponse<>();

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<Response<UserObject>> register(@Valid @RequestBody UserObject registerDto) {
        UserObject user = authService.register(registerDto);
        return ResponseEntity.ok(response.createSuccessResponse(user, "User registered successfully.", CustomStatusCode.USER_REGISTERED_SUCCESSFULLY));
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<Response<Map<String, String>>> login( @RequestBody UserObject loginDto) {
        Map<String, String> login = authService.login(loginDto);
        GenericResponse<Map<String, String>> response = new GenericResponse<>();
        return ResponseEntity.ok(response.createSuccessResponse(login, "Login successful.", CustomStatusCode.LOGIN_SUCCESSFUL));
    }

    // Refresh token
    @PostMapping("/refresh")
    public ResponseEntity<Response<Map<String, String>>> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        Map<String, String> newToken = authService.refreshToken(refreshToken);
        GenericResponse<Map<String, String>> response = new GenericResponse<>();
        return ResponseEntity.ok(response.createSuccessResponse(newToken, "Token refreshed successfully.", CustomStatusCode.TOKEN_REFRESHED_SUCCESSFULLY));
    }

    // Logout user
    @PostMapping("/logout")
    public ResponseEntity<Response<UserObject>> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok(response.createSuccessResponse(null, "Logout successful.", CustomStatusCode.LOGOUT_SUCCESSFUL));
    }

    // Get current user details
    @GetMapping("/me")
    public ResponseEntity<Response<UserObject>> getCurrentUser( @RequestHeader("Authorization") String token) {
        UserObject currentUser = authService.getCurrentUser(token);
        return ResponseEntity.ok(response.createSuccessResponse(currentUser, "Current user retrieved successfully.", CustomStatusCode.USER_RETRIEVED_SUCCESSFULLY));
    }
}
