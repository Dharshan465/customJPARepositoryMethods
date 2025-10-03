package ford.relationalMapping.assignment19.LibraryManagementSystem.repository;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    Page<Book> findAll(Pageable pageable);
}
