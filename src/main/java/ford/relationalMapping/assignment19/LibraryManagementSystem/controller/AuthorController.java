package ford.relationalMapping.assignment19.LibraryManagementSystem.controller;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.AuthorDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.AuthorDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookCreationDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO newAuthor = authorService.addAuthor(authorDTO);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @PostMapping("/{authorId}/books")
    public ResponseEntity<BookDTO> addBookForAuthor(@PathVariable Long authorId, @Valid @RequestBody BookCreationDTO bookCreationDTO) {
        BookDTO newBook = authorService.addBookForAuthor(authorId, bookCreationDTO);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long authorId) {
        List<BookDTO> books = authorService.getBooksByAuthor(authorId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDetailDTO> getAuthorWithBooks(@PathVariable Long authorId) {
        AuthorDetailDTO author = authorService.getAuthorWithBooks(authorId);
        return ResponseEntity.ok(author);
    }


}
