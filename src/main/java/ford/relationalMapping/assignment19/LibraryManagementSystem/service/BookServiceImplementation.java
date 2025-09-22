package ford.relationalMapping.assignment19.LibraryManagementSystem.service;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.AuthorDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BorrowingMemberDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Book;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.BookNotFoundException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.repository.BookRepository;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public BookDetailDTO getBookWithBorrowers(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));

        // Accessing author and borrowedByMembers triggers lazy loading
        AuthorDTO authorDTO = new AuthorDTO(
                book.getAuthor().getId(),
                book.getAuthor().getName(),
                book.getAuthor().getNationality()
        );

        List<BorrowingMemberDTO> membersDTO = book.getBorrowedByMembers().stream()
                .map(member -> new BorrowingMemberDTO(member.getId(), member.getName(), member.getEmail()))
                .collect(Collectors.toList());

        return new BookDetailDTO(book.getId(), book.getTitle(), book.getIsbn(), authorDTO, membersDTO);
    }
}
