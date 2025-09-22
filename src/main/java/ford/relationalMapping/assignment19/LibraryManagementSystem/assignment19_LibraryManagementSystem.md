# Assignment 19: Library Management System - JPA Relations + Exception Handling

This document details the implementation of a Library Management System using Spring Boot, Spring Data JPA, and REST APIs, focusing on various JPA relationships, DTOs, business rules, and robust exception handling.


## 1. Problem Statement 

The goal is to develop a Library Management System that handles `Books`, `Authors`, `Members`, and `MembershipCards`. The system needs to manage their relationships, enforce specific business rules, and handle invalid requests gracefully using custom exceptions. The development was structured in phases:

*   **Phase 1: Member & MembershipCard (One-to-One)**
    *   Each Member must have exactly one MembershipCard.
    *   A MembershipCard stores card number, issue date, expiry date.
*   **Phase 2: Author & Books (One-to-Many)**
    *   An Author can write many Books.
    *   A Book must belong to exactly one Author.
*   **Phase 3: Members & Books (Many-to-Many Borrowing)**
    *   A Member can borrow multiple Books.
    *   A Book can be borrowed by multiple Members.
    *   Specific borrowing rules apply (e.g., expired cards, borrowing limits, duplicate borrows).

## 2. Key Features

*   Implemented `One-to-One`, `One-to-Many`, and `Many-to-Many` mappings in a real-world system using Spring Data JPA.
*   Applied incremental development by structuring the problem into phases.
*   Enforced business rules within the service layer.
*   Implemented custom exception classes for specific error scenarios.
*   Created a global exception handler using `@RestControllerAdvice` to manage all exceptions gracefully.
*   Utilized Data Transfer Objects (DTOs) for effective data communication between layers and to prevent infinite recursion in bidirectional relationships.
*   Demonstrated lazy loading mechanisms with `@Transactional` annotations.
*   Used `CommandLineRunner` for automated sample data setup and `SpringDoc OpenAPI (Swagger UI)` for interactive API testing.

## 3. Entities and Relationships

The system comprises the following JPA entities with their defined relationships:

1.  **`Member`**
    *   `id` (Primary Key)
    *   `name`
    *   `email` (Unique)
    *   `membershipCard` (`One-to-One` with `MembershipCard`, owning side, `CascadeType.ALL`, `orphanRemoval=true`, `FetchType.LAZY`)
    *   `borrowedBooks` (`Many-to-Many` with `Book`, inverse side, `FetchType.LAZY`)

2.  **`MembershipCard`**
    *   `id` (Primary Key)
    *   `cardNumber` (Unique)
    *   `issueDate`
    *   `expiryDate`
    *   `member` (`One-to-One` with `Member`, inverse side, `mappedBy="membershipCard"`, `FetchType.LAZY`, `@JsonIgnore`)

3.  **`Author`**
    *   `id` (Primary Key)
    *   `name`
    *   `nationality`
    *   `books` (`One-to-Many` with `Book`, inverse side, `mappedBy="author"`, `CascadeType.ALL`, `orphanRemoval=true`, `FetchType.LAZY`, `@JsonIgnore`)

4.  **`Book`**
    *   `id` (Primary Key)
    *   `title`
    *   `isbn` (Unique, 13-character pattern)
    *   `author` (`Many-to-One` with `Author`, owning side, `FetchType.LAZY`)
    *   `borrowedByMembers` (`Many-to-Many` with `Member`, owning side, `mappedBy="borrowedBooks"`, `FetchType.LAZY`, `@JsonIgnore`)

## 4. REST Endpoints

The following REST endpoints are implemented:

### Member Endpoints (`/members`)
*   `POST /members` : Register a new member with a membership card.
*   `GET /members/{id}` : Fetch member details including their membership card and borrowed books.
*   `GET /members/{memberId}/books` : List all books currently borrowed by a specific member.
*   `POST /members/{memberId}/borrow/{bookId}` : Allow a member to borrow a specific book, enforcing business rules.
*   `DELETE /members/{memberId}/return/{bookId}` : Allow a member to return a specific book.

### Author Endpoints (`/authors`)
*   `POST /authors` : Add a new author.
*   `POST /authors/{authorId}/books` : Add a new book for a specific author.
*   `GET /authors/{authorId}` : Fetch author details including a list of their books.
*   `GET /authors/{authorId}/books` : Get all books written by a specific author.

### Book Endpoints (`/books`)
*   `GET /books/{id}/members` : Fetch book details including a list of members who have borrowed it.

## 5. Custom Exceptions and Global Handler

To handle specific error scenarios gracefully, the following custom exceptions are defined:

*   `MemberNotFoundException`
*   `InvalidCardException`
*   `AuthorNotFoundException`
*   `BookAlreadyExistsException`
*   `BookNotFoundException`
*   `BorrowingLimitExceededException`
*   `DuplicateBorrowException`
*   `ExpiredMembershipException`

A `GlobalExceptionHandler` (`@ControllerAdvice`) catches these custom exceptions (and standard Spring exceptions like `DataIntegrityViolationException`, `MethodArgumentNotValidException`) and returns a standardized `ErrorResponse` DTO with appropriate HTTP status codes (e.g., `404 Not Found`, `400 Bad Request`, `403 Forbidden`, `409 Conflict`).

## 6. Data Transfer Objects (DTOs)

Multiple DTOs are used to control the data exposed via the API, prevent infinite recursion in bidirectional relationships, and tailor responses to specific endpoint needs:

*   `MembershipCardDTO`
*   `MemberCreationDTO` (for `POST /members`)
*   `MemberDTO` (basic member info)
*   `MemberDetailDTO` (for `GET /members/{id}`, includes card and borrowed books)
*   `AuthorDTO` (basic author info)
*   `AuthorDetailDTO` (for `GET /authors/{authorId}`, includes author's books)
*   `BookCreationDTO` (for `POST /authors/{authorId}/books`)
*   `BookDTO` (basic book info, includes author)
*   `BorrowedBookDTO` (simplified book info for member's borrowed list)
*   `BorrowingMemberDTO` (simplified member info for book's borrowers list)
*   `BookDetailDTO` (for `GET /books/{id}/members`, includes book's borrowers)

## 7. Project Structure

The project follows a standard Spring Boot layered architecture:

```LibraryManagementSystem
    ├── LibraryManagementSystemApplication.java // Main Spring Boot application
    ├── controller // REST API Endpoints
    │   ├── AuthorController.java
    │   ├── BookController.java
    │   └── MemberController.java
    ├── dto // Data Transfer Objects
    │   ├── AuthorDTO.java
    │   ├── AuthorDetailDTO.java
    │   ├── BookCreationDTO.java
    │   ├── BookDTO.java
    │   ├── BookDetailDTO.java
    │   ├── BorrowedBookDTO.java
    │   ├── BorrowingMemberDTO.java
    │   ├── MemberCreationDTO.java
    │   ├── MemberDTO.java
    │   ├── MemberDetailDTO.java
    │   └── MembershipCardDTO.java
    ├── entity // JPA Entities
    │   ├── Author.java
    │   ├── Book.java
    │   ├── Member.java
    │   └── MembershipCard.java
    ├── exception // Custom Exceptions & Global Handler
    │   ├── AuthorNotFoundException.java
    │   ├── BookAlreadyExistsException.java
    │   ├── BookNotFoundException.java
    │   ├── DuplicateBorrowException.java
    │   ├── ErrorResponse.java
    │   ├── ExpiredMembershipException.java
    │   ├── GlobalExceptionHandler.java
    │   ├── InvalidCardException.java
    │   ├── MemberNotFoundException.java
    │   ├── BorrowingLimitExceededException.java
    │   └── DataIntegrityViolationException.java
    ├── repository // Spring Data JPA Repositories
    │   ├── AuthorRepository.java
    │   ├── BookRepository.java
    │   ├── MemberRepository.java
    │   └── MembershipCardRepository.java
    └── service // Business Logic (Service Interfaces & Implementations)
        ├── AuthorService.java
        ├── BookService.java
        ├── MemberService.java
        ├── AuthorServiceImplementation.java
        ├── BookServiceImplementation.java
        └── MemberServiceImplementation.java
```

## Github Repository Link : 



