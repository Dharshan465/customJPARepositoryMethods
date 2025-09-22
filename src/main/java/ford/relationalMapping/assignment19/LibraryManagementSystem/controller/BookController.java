package ford.relationalMapping.assignment19.LibraryManagementSystem.controller;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<BookDetailDTO> getBookWithBorrowers(@PathVariable Long id) {
        BookDetailDTO book = bookService.getBookWithBorrowers(id);
        return ResponseEntity.ok(book);
    }
}
