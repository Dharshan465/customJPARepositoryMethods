package ford.relationalMapping.assignment17.ECommerceSystem.repository;

import ford.relationalMapping.assignment17.ECommerceSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
