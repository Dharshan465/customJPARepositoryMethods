package ford.relationalMapping.assignment17.ECommerceSystem.repository;

import ford.relationalMapping.assignment17.ECommerceSystem.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(Long userId);
}
