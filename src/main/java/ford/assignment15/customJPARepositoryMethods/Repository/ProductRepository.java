package ford.assignment15.customJPARepositoryMethods.Repository;

import ford.assignment15.customJPARepositoryMethods.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
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




}
