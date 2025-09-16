# Custom JPA Repository Methods

This document outlines various interesting custom methods that can be implemented in a JPA repository for effective data management and querying.

## 1. Search & Filtering

Methods for searching and filtering products based on various criteria.

*   **Find all products within a price range:**
    ```java
    List<Product> findByPriceBetween(Double min, Double max);
    ```

*   **Find all products by partial match on name (case-insensitive):**
    ```java
    List<Product> findByNameContainingIgnoreCase(String keyword);
    ```

*   **Find all products by multiple categories:**
    ```java
    List<Product> findByCategoryIn(List<String> categories);
    ```

## 2. Sorting & Ranking

Methods for sorting products and ranking them based on specific attributes.

*   **Find the top 3 cheapest products in a category:**
    ```java
    List<Product> findTop3ByCategoryOrderByPriceAsc(String category);
    ```

*   **Find the newest products added last week:**
    ```java
    List<Product> findByCreatedDateAfter(LocalDate date);
    ```

## 3. Aggregation Queries (JPQL / Native)

Complex queries using JPQL to perform aggregations like averaging, counting, and summing.

*   **Find the average price per category:**
    ```java
    @Query("SELECT p.category, AVG(p.price) FROM Product p GROUP BY p.category")
    List<Object[]> findAveragePriceByCategory();
    ```

*   **Find the category with maximum number of products:**
    ```java
    @Query("SELECT p.category, COUNT(p) as count FROM Product p GROUP BY p.category ORDER BY count DESC")
    List<Object[]> findMostPopularCategory();
    ```

*   **Find total stock value per category (SUM(price * stock)):**
    ```java
    @Query("SELECT p.category, SUM(p.price * p.stock) FROM Product p GROUP BY p.category")
    List<Object[]> calculateStockValueByCategory();
    ```

## 4. Inventory Management

Methods specifically designed for managing product inventory.

*   **Find out-of-stock products:**
    ```java
    List<Product> findByStockEquals(Integer stock);
    ```

*   **Find low-stock products (stock < 10):**
    ```java
    List<Product> findByStockLessThan(Integer stock);
    ```

*   **Find products that are about to expire (if expiryDate field exists):**
    ```java
    List<Product> findByExpiryDateBefore(LocalDate date);
    ```

## 5. Bulk Update/Delete

Methods for performing bulk update or delete operations efficiently.

*   **Increase price by 10% for all products in a category:**
    ```java
    @Modifying
    @Query("UPDATE Product p SET p.price = p.price * 1.1 WHERE p.category = :category")
    int increasePriceByCategory(@Param("category") String category);
    ```

*   **Delete all discontinued products:**
    ```java
    @Modifying
    @Query("DELETE FROM Product p WHERE p.stock = 0")
    int deleteOutOfStockProducts();
    ```

## 6. Advanced Queries

More complex query patterns for specific business logic.

*   **Find most expensive product in each category:**
    ```java
    @Query("SELECT p FROM Product p WHERE p.price = (SELECT MAX(p2.price) FROM Product p2 WHERE p2.category = p.category)")
    List<Product> findMostExpensiveProductInEachCategory();
    ```

*   **Find products not purchased in last 6 months (if purchases exist):**
    ```java
    @Query("SELECT p FROM Product p WHERE p.id NOT IN (SELECT od.product.id FROM OrderDetail od WHERE od.order.date > :date)")
    List<Product> findUnpurchasedProducts(@Param("date") LocalDate date);
    ```

*   **Find products that belong to multiple categories (if many-to-many existed).**
    *(This would require a specific query based on the many-to-many relationship implementation, e.g., using `JOIN FETCH` or a subquery.)*

## 7. Pagination & Performance

Methods incorporating pagination for handling large datasets and improving performance.

*   **Find all products in a category with pagination:**
    ```java
    Page<Product> findByCategory(String category, Pageable pageable);
    ```

*   **Find the cheapest products with pagination:**
    ```java
    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);
    ```
