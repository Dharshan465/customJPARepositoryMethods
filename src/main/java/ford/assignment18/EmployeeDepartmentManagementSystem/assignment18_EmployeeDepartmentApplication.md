# Employee–Department Management System

## Problem Statement

A company needs to manage its employee and department details within its HR system. The core relationship is that each Department can contain multiple Employees, but every Employee must belong to exactly one Department. The task is to design and implement this system using Spring Boot and Spring Data JPA, focusing on proper entity relationships, REST API design, and lazy loading mechanisms.

## Functional Requirements

### 1. Entities

*   **Department**
    *   `id` (Primary Key)
    *   `name` (Unique, Non-nullable)
    *   `location`

*   **Employee**
    *   `id` (Primary Key)
    *   `firstName`
    *   `lastName`
    *   `email` (Unique)
    *   `salary`
    *   `department` (Many-to-One relationship with Department)

### 2. Relationships

*   A Department can have many Employees (One-to-Many).
*   An Employee belongs to one Department (Many-to-One).
*   Both sides of the relationship must use **LAZY loading**.

### 3. REST Endpoints

*   **`POST /departments`**
    *   Creates a new Department.

*   **`POST /employees`**
    *   Creates a new Employee and assigns them to an existing department.

*   **`GET /departments/{id}`**
    *   Retrieves Department details, including all associated employees (should properly trigger lazy loading).

*   **`GET /employees/{id}`**
    *   Retrieves Employee details, including their department information (lazy loaded).

*   **`GET /departments`**
    *   Lists all departments **without** fetching their associated employees (to demonstrate lazy fetching).

*   **`DELETE /departments/{id}`**
    *   Deletes a department. If orphan removal is enabled, this should also remove its associated employees.

### 4. Lazy Loading Requirement

*   Employees belonging to a department should not be fetched automatically when the department is loaded, unless explicitly accessed.
*   Similarly, the department associated with an Employee should be fetched lazily.

## Technical Requirements

*   Utilize **Spring Boot** and **Spring Data JPA**.
*   Configure `@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)` on the `Department` entity.
*   Configure `@ManyToOne(fetch = FetchType.LAZY)` on the `Employee` entity.
*   Implement the `@Transactional` annotation in the Service layer to correctly handle lazy loading when fetching related entities.
*   Employ **DTOs** (Data Transfer Objects) to prevent infinite recursion during JSON serialization. Use either `@JsonIgnore` or the `@JsonManagedReference` / `@JsonBackReference` annotations.

## Assignment Tasks : 

1.  Create the `Department` and `Employee` entities, ensuring proper JPA annotations are applied.
2.  Implement Spring Data JPA repositories for both entities.
3.  Develop service classes to encapsulate business logic for creating, fetching, and deleting entities.
4.  Expose the required REST controllers to handle incoming HTTP requests.
5.  Thoroughly test the lazy loading behavior:
    *   Fetch a department without its employees.
    *   Fetch an employee with their department details.
    *   Access the employees list of a department within a `@Transactional` method to observe lazy loading in action.
6.  Add sample data for testing purposes using `data.sql` or a `CommandLineRunner`.

## Important Notes

*   **Preventing Infinite Recursion in JSON Serialization:**
    *   For bidirectional relationships, use:
        *   `@JsonManagedReference` on the collection side (e.g., `Department.employees`).
        *   `@JsonBackReference` on the owning side (e.g., `Employee.department`).
    *   Alternatively, use `@JsonIgnoreProperties({"employees"})` on the `Department` entity to ignore the `employees` collection during serialization when fetching a department.

*   **Demonstrating Lazy Loading:**
    *   First, fetch a `Department` entity. At this point, its `employees` collection should not be populated.
    *   Then, explicitly access `department.getEmployees()` inside a method annotated with `@Transactional` to trigger the lazy loading of the employees.

## GitHub Repository: https://github.com/Dharshan465/customJPARepositoryMethods/tree/main/src/main/java/ford/assignment18/EmployeeDepartmentManagementSystem
