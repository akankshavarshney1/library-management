package com.example.library_management.utils.enums;

public enum RoleType {
    ADMIN,
    LIBRARIAN,
    USER;

    public static RoleType getRoleTypeByString(String role) {
        try {
            return RoleType.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
