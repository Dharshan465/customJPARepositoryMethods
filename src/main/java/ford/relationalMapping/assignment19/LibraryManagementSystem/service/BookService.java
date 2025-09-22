package ford.relationalMapping.assignment19.LibraryManagementSystem.service;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDetailDTO;

public interface BookService {
    BookDetailDTO getBookWithBorrowers(Long bookId);
}
