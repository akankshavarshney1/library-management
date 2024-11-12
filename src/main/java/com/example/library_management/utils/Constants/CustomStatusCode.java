package com.example.library_management.utils.Constants;

public interface CustomStatusCode {

    // ==============================
    // Success Codes
    // ==============================
    int USER_REGISTERED_SUCCESSFULLY = 1001;
    int LOGIN_SUCCESSFUL = 1002;


    // ==============================
    // JWT Token Related Codes
    // ==============================
    int INVALID_TOKEN = 6001;
    int TOKEN_EXPIRED = 6002;


    // ==============================
    // User Related Codes
    // ==============================
    int USER_LOGOUT_SUCCESSFUL = 7003;
    int USER_RETRIEVED_SUCCESSFULLY = 8003;
    int USER_UPDATED_SUCCESSFULLY = 8004;
    int USER_DELETED_SUCCESSFULLY = 8005;
    int USER_ROLE_UPDATED_SUCCESSFULLY = 8006;
    int USERS_RETRIEVED_SUCCESSFULLY = 8019;
    int USER_NOT_FOUND = 1819;

    // ==============================
    // Book Related Codes
    // ==============================
    int BOOK_RETRIEVED_SUCCESSFULLY = 8007;
    int BOOK_CREATED_SUCCESSFULLY = 8008;
    int BOOK_UPDATED_SUCCESSFULLY = 8009;
    int BOOK_DELETED_SUCCESSFULLY = 8010;
    int BOOKS_RETRIEVED_SUCCESSFULLY = 8018;

    // ==============================
    // Author Related Codes
    // ==============================
    int AUTHOR_UPDATED_SUCCESSFULLY = 8011;
    int AUTHOR_DELETED_SUCCESSFULLY = 8012;
    int AUTHOR_CREATED_SUCCESSFULLY = 8013;
    int AUTHOR_RETRIEVED_SUCCESSFULLY = 8014;
    int AUTHORS_RETRIEVED_SUCCESSFULLY = 8017;

    // ==============================
    // General Logout and Token Refresh Codes
    // ==============================
    int LOGOUT_SUCCESSFUL = 8015;
    int TOKEN_REFRESHED_SUCCESSFULLY = 8016;

    // ==============================
    // Error
    // ==============================


    int INVALID_USER_ID = 8017;
    int INTERNAL_APPLICATION_ERROR = 1818;

    int COMPROMISED_PASSWORD = 2000;
    int  SERVER_ERROR = 2001;
}
