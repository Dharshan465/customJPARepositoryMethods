package ford.relationalMapping.assignment19.LibraryManagementSystem.controller;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    
    @GetMapping("/search/year")
    public List<BookDTO> searchBooksByPublicationYear(@RequestParam Integer year) {
        return bookService.searchBooksByPublicationYear(year);
    }


    @GetMapping("/page/year")
    public Page<BookDTO>getBooksByPublicationYear(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "asc") String sort) {
        return bookService.getBooksByPublicationYear(page, size,sort);

    }
}
