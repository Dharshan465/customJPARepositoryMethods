package ford.relationalMapping.assignment19.LibraryManagementSystem.repository;

import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    //Page<Book> findAll(Pageable pageable);

    List<Book> findAll();
}
