package ford.relationalMapping.assignment19.LibraryManagementSystem.service;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.AuthorDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.AuthorDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookCreationDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Author;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Book;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.AuthorNotFoundException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.BookAlreadyExistsException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.repository.AuthorRepository;
import ford.relationalMapping.assignment19.LibraryManagementSystem.repository.BookRepository;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImplementation implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImplementation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public AuthorDTO addAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setNationality(authorDTO.getNationality());
        Author savedAuthor = authorRepository.save(author);
        return new AuthorDTO(savedAuthor.getId(), savedAuthor.getName(), savedAuthor.getNationality());
    }

    @Override
    @Transactional
    public BookDTO addBookForAuthor(Long authorId, BookCreationDTO bookCreationDTO) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + authorId));

        if (bookRepository.findByIsbn(bookCreationDTO.getIsbn()).isPresent()) {
            throw new BookAlreadyExistsException("Book with ISBN " + bookCreationDTO.getIsbn() + " already exists.");
        }

        Book book = new Book();
        book.setTitle(bookCreationDTO.getTitle());
        book.setIsbn(bookCreationDTO.getIsbn());
        book.setAuthor(author); // Set the relationship

        Book savedBook = bookRepository.save(book);
        author.addBook(savedBook); // Ensure bidirectional consistency

        return mapToBookDTO(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDetailDTO getAuthorWithBooks(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + authorId));

        // Accessing books triggers lazy loading
        List<BookDTO> booksDTO = author.getBooks().stream()
                .map(this::mapToBookDTO)
                .collect(Collectors.toList());

        return new AuthorDetailDTO(author.getId(), author.getName(), author.getNationality(), booksDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + authorId));

        return author.getBooks().stream()
                .map(this::mapToBookDTO)
                .collect(Collectors.toList());
    }

    private BookDTO mapToBookDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName(), book.getAuthor().getNationality())
        );
    }
}
