package ford.relationalMapping.assignment19.LibraryManagementSystem.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BookCreationDTO {
    @NotBlank(message = "Book title cannot be empty")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "ISBN cannot be empty")
    private String isbn;

    @NotBlank(message = "Publication year cannot be empty")
    @Pattern(regexp = "\\d{3,4}", message = "Publication year must be a valid year")
    @Column( nullable = false)
    private Integer publicationYear;

    public BookCreationDTO() {
    }

    public BookCreationDTO(String title, String isbn,Integer publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
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
}
