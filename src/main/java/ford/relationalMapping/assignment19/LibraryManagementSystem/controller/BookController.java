package ford.relationalMapping.assignment19.LibraryManagementSystem.controller;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BookDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Book;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin("http://localhost:4200/")
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
    @GetMapping("/all")
    public List<BookDetailDTO> getAllBooks() {
        System.out.println("Fetching all books...");
        System.out.println(bookService.getAllBooks());
        return bookService.getAllBooks();
    }
}
