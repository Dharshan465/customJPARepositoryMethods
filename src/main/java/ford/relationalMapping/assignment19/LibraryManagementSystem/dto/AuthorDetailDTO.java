package ford.relationalMapping.assignment19.LibraryManagementSystem.dto;

import java.util.List;

public class AuthorDetailDTO {
    private Long id;
    private String name;
    private String nationality;
    private List<BookDTO> books; // List of books by this author

    public AuthorDetailDTO() {
    }

    public AuthorDetailDTO(Long id, String name, String nationality, List<BookDTO> books) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.books = books;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
