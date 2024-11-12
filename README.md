Library Management System
A Spring Boot-based REST API for managing library resources, users, and roles with role-based access control (RBAC) and JWT authentication.

Features
-Authentication & Authorization
    User registration and login
    Role-based access control: Admin, Librarian, and Member roles
		Secure JWT-based token management
-Resource Management
		CRUD operations for managing Books and Authors
		Transaction management for book issuance and return
-Error Handling & Validation
		Custom error handling with standardized status codes
		Validation for user inputs and requests

- Project Structure
		Entities: Defines core entities such as User, Book, Author, and Transaction.
		DTOs: Data Transfer Objects to structure incoming and outgoing requests.
		Controllers: Defines API endpoints for different resources.
		Services: Contains business logic for the library operations.
		Repositories: Manages database interactions with JPA.
		Utilities: Helper classes, constants, and enums (e.g., status codes, role types).

-Tech Stack
   Backend & Security: Java, Spring Boot, Spring Security, JWT
   Database: Hibernate, JPA, MySQL
   Validation: Jakarta Bean Validation


API Requirements

Authentication Endpoints:
POST /api/auth/register - Public access
POST /api/auth/login - Public access
POST /api/auth/refresh - Authenticated users
POST /api/auth/logout - Authenticated users
GET /api/auth/me - Authenticated users

User Management Endpoints (ADMIN only):
GET /api/users - Get all users
GET /api/users/{id} - Get user by ID
PUT /api/users/{id} - Update user
DELETE /api/users/{id} - Delete user
PUT /api/users/{id}/role - Change user role

Book Endpoints:
GET /api/books - Accessible by all authenticated users
GET /api/books/{id} - Accessible by all authenticated users
POST /api/books - Accessible by ADMIN, LIBRARIAN
PUT /api/books/{id} - Accessible by ADMIN, LIBRARIAN
DELETE /api/books/{id} - Accessible by ADMIN only

Author Endpoints:
GET /api/authors - Accessible by all authenticated users
GET /api/authors/{id} - Accessible by all authenticated users
POST /api/authors - Accessible by ADMIN, LIBRARIAN
PUT /api/authors/{id} - Accessible by ADMIN, LIBRARIAN
DELETE /api/authors/{id} - Accessible by ADMIN only

