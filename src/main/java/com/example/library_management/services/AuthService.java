package com.example.library_management.services;

import com.example.library_management.dto.Object.UserObject;
import com.example.library_management.entities.User;

import java.util.Map;

public interface AuthService {

    UserObject register(UserObject registerDto);

    Map<String, String> login(UserObject loginDto);

    Map<String, String> refreshToken(String refreshToken);

    void logout(String token);

    UserObject getCurrentUser(String token);
}
