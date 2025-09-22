package ford.assignment15.customJPARepositoryMethods.Repository;

import ford.assignment15.customJPARepositoryMethods.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    //--------------------------------------------------------------------------------------------------------

    // Assignment on custom JPA Repository methods

    //--------------------------------------------------------------------------------------------------------

    //1.Searching and Filtering

    //i)Find all products within a price range:

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    //ii)Find all products by partial match on name (case-insensitive):

    List<Product> findByNameContainingIgnoreCase(String keyword);

    //iii)Find all products by multiple categories:

    List<Product> findByCategoryIn(List<String> categories);

    //--------------------------------------------------------------------------------------------------------

    //2.Sorting and Ranking

    //i)Find the top 3 cheapest products in a specific category:

    List<Product>findTop3ByCategoryOrderByPriceAsc(String category);

    //ii)Find the newest products added last week:

    List<Product>findByCreatedDateAfter(LocalDate date);

    //---------------------------------------------------------------------------------------------------

    //3. Aggregation Queries (JPQL / Native)

    //i)Find average price per category (JPQL):

    @Query("SELECT p.category, AVG(p.price) FROM Product p GROUP BY p.category")
    List<Object[]> findAveragePricePerCategory();

    //ii)Find category with max no of products:

    @Query("select p.category, COUNT(p) as productCount from Product p GROUP BY p.category ORDER BY productCount DESC")
    List<Object[]> findCategoryWithMaxProducts();

    //iii) Find total stock value per category (SUM(price * stock)):

    @Query("SELECT p.category, SUM(p.price * p.stock) FROM Product p GROUP BY p.category")
    List<Object[]> findTotalStockValuePerCategory();

    //---------------------------------------------------------------------------------------------------

    //4.Inventory Management

    //i)Find Out of stock products:

    List<Product>findByStockEquals(Integer stock);

    //ii) Find low-stock products (stock < threshold):

    List<Product> findByStockLessThan(Integer stock);

    //iii) Find products about to expire :

    List<Product> findByExpiryDateBefore(LocalDate date);

    //---------------------------------------------------------------------------------------------------

    //5.Bulk Updates and Deletes

    //i)Increase price by percentage for a category:

    @Modifying
    @Query("UPDATE Product p SET p.price = p.price * (1 + :percentage / 100) WHERE p.category = :category")
    int increasePriceByPercentageForCategory(@Param("percentage") Double percentage, @Param("category") String category);

    //ii)Delete discontinued products:

    @Modifying
    @Query("DELETE FROM Product p WHERE p.stock=0")
    int deleteDiscontinuedProducts();

    //---------------------------------------------------------------------------------------------------

    //6.Advanced Queries

    //i)Find Most Expensive Product in Each Category:

    @Query("SELECT p1 FROM Product p1 WHERE p1.price = (SELECT MAX(p2.price) FROM Product p2 WHERE p2.category = p1.category)")
    List<Product> findMostExpensiveProductInEachCategory();

    //ii)Find products not purchased in last 6 months (Assuming a Purchase entity exists):

//    @Query("SELECT p FROM Product p WHERE p.id NOT IN (SELECT DISTINCT pur.product.id FROM Purchase pur WHERE pur.purchaseDate >= :cutoffDate)")
//    List<Product> findProductsNotPurchasedInLast6Months(@Param("cutoffDate") LocalDateTime cutoffDate);

    //iii)Find Products that belong to multiple categories (if many to many mapping exists):

//    @Query("SELECT p FROM Product p JOIN p.categories c GROUP BY p HAVING COUNT(c) > 1")
//    List<Product> findProductsInMultipleCategories();

    //---------------------------------------------------------------------------------------------------

    //7.Pagination and Performance Optimization

    //i)find all products in a category with pagination support

    Page<Product> findByCategory(String category, Pageable pageable);

    //ii) Find cheapest products with pagination

    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);

    //---------------------------------------------------------------------------------------------------











}
