package ford.relationalMapping.assignment19.LibraryManagementSystem.service;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDetailDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    BookDetailDTO getBookWithBorrowers(Long bookId);

    Page<BookDTO> getBooksByPublicationYear(int page, int size, String sort);

    List<BookDTO> searchBooksByPublicationYear(Integer year);
}
