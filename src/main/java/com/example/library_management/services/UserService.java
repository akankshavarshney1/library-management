package com.example.library_management.services;

import com.example.library_management.dto.Object.UserObject;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserObject> getAllUsers();

    Optional<UserObject> getUserById(Long id);

    UserObject updateUser(Long id, UserObject userUpdateDTO);

    void deleteUser(Long id);

    UserObject updateUserRole(Long id, UserObject userRoleUpdateDTO);
}
