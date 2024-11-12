package com.example.library_management.controller;

import com.example.library_management.dto.Object.UserObject;
import com.example.library_management.dto.Response.Response;
import com.example.library_management.services.UserService;
import com.example.library_management.utils.Constants.CustomStatusCode;
import com.example.library_management.dto.Response.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;
    private final GenericResponse<UserObject> response = new GenericResponse<>();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Response<List<UserObject>>> getAllUsers() {
        List<UserObject> users = userService.getAllUsers();
        GenericResponse<List<UserObject>> response = new GenericResponse<>();
        return ResponseEntity.ok(response.createSuccessResponse(users, "Users retrieved successfully.", CustomStatusCode.USERS_RETRIEVED_SUCCESSFULLY));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<UserObject>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(response.createSuccessResponse(user, "User retrieved successfully.", CustomStatusCode.USER_RETRIEVED_SUCCESSFULLY)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UserObject>> updateUser(@PathVariable Long id, @RequestBody UserObject userDto) {
        UserObject updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(response.createSuccessResponse(updatedUser, "User updated successfully.", CustomStatusCode.USER_UPDATED_SUCCESSFULLY));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<UserObject>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(response.createSuccessResponse(null, "User deleted successfully.", CustomStatusCode.USER_DELETED_SUCCESSFULLY));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<Response<UserObject>> changeUserRole(@PathVariable Long id, @RequestBody UserObject userRoleDto) {
        UserObject updatedUser = userService.updateUserRole(id, userRoleDto);
        return ResponseEntity.ok(response.createSuccessResponse(updatedUser, "User role updated successfully.", CustomStatusCode.USER_ROLE_UPDATED_SUCCESSFULLY));
    }


}
