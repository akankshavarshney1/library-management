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
