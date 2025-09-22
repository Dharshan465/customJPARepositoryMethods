package ford.relationalMapping.assignment19.LibraryManagementSystem.repository;

import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
