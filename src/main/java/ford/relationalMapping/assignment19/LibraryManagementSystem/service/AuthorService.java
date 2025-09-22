package ford.relationalMapping.assignment19.LibraryManagementSystem.service;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.AuthorDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.AuthorDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookCreationDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO addAuthor(AuthorDTO authorDTO);
    BookDTO addBookForAuthor(Long authorId, BookCreationDTO bookCreationDTO);
    AuthorDetailDTO getAuthorWithBooks(Long authorId);
    List<BookDTO> getBooksByAuthor(Long authorId);
}
