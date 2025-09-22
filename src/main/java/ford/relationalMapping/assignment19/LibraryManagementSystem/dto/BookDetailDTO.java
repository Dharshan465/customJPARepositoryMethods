package ford.relationalMapping.assignment19.LibraryManagementSystem.dto;

import java.util.List;

public class BookDetailDTO {
    private Long id;
    private String title;
    private String isbn;
    private AuthorDTO author;
    private List<BorrowingMemberDTO> borrowedByMembers; // List of members who borrowed this book

    public BookDetailDTO() {
    }

    public BookDetailDTO(Long id, String title, String isbn, AuthorDTO author, List<BorrowingMemberDTO> borrowedByMembers) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.borrowedByMembers = borrowedByMembers;
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

    public List<BorrowingMemberDTO> getBorrowedByMembers() {
        return borrowedByMembers;
    }

    public void setBorrowedByMembers(List<BorrowingMemberDTO> borrowedByMembers) {
        this.borrowedByMembers = borrowedByMembers;
    }
}
