package ford.relationalMapping.assignment19.LibraryManagementSystem.dto;

public class BorrowedBookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private AuthorDTO author;

    public BorrowedBookDTO() {
    }

    public BorrowedBookDTO(Long id, String title, String isbn,Integer publicationYear, AuthorDTO author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.author = author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }
}
