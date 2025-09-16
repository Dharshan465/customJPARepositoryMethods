# E-Commerce System – User ↔ Cart (One-to-One Only)

This document describes a simplified E-Commerce system focusing on a strict one-to-one relationship between a User and their Cart. The system allows for basic user management and cart manipulation, simulating item additions and removals by directly updating cart totals.

## System Overview

*   A User has exactly one Cart.
*   The Cart tracks simple information: the total number of items (`totalItems`) and the total price (`totalPrice`).
*   Students can simulate “adding/removing items” by updating these fields directly via provided API endpoints.

## Entities

### 1. User

Represents a user in the system.

*   `id` (PK, Long)
*   `username` (unique)
*   `email` (unique)
*   `password` (hidden in responses)
*   **Relationship:** One-to-One mapping with `Cart`

### 2. Cart

Represents a shopping cart associated with a user.

*   `id` (PK, Long)
*   `createdAt` (LocalDateTime)
*   `totalItems` (int)
*   `totalPrice` (double)
*   **Relationship:** One-to-One mapping with `User`

## API Endpoints

### User Management

1.  **`POST /users`**
    *   **Description:** Creates a new user with an automatically associated empty cart (`totalItems = 0`, `totalPrice = 0.0`).
    *   **Request Body Example:**
        ```json
        {
          "username": "john_doe",
          "email": "john@example.com",
          "password": "securePass123"
        }
        ```

2.  **`GET /users/{id}`**
    *   **Description:** Fetches a specific user along with their associated cart details.

3.  **`DELETE /users/{id}`**
    *   **Description:** Deletes a user and their associated cart.

### Cart Management (One-to-One focus)

4.  **`GET /users/{id}/cart`**
    *   **Description:** Fetches the cart belonging to a specific user.
    *   **Response Body Example:**
        ```json
        {
          "id": 1,
          "createdAt": "2025-09-15T12:00:00",
          "totalItems": 3,
          "totalPrice": 150.00
        }
        ```

5.  **`PUT /users/{id}/cart`**
    *   **Description:** Manually updates a user's cart, allowing for direct modification of `totalItems` and `totalPrice`.
    *   **Request Body Example:**
        ```json
        {
          "totalItems": 5,
          "totalPrice": 250.00
        }
        ```

6.  **`PATCH /users/{id}/cart/add`**
    *   **Description:** Increments cart totals, simulating the action of “adding items” to the cart. (Implementation details for incrementing `totalItems` and `totalPrice` would be defined in the backend.)

7.  **`PATCH /users/{id}/cart/remove`**
    *   **Description:** Decrements cart totals, simulating the action of “removing items” from the cart. (Implementation details for decrementing `totalItems` and `totalPrice` would be defined in the backend.)

