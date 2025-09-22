package ford.relationalMapping.assignment19.LibraryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class MembershipCardDTO {
    private Long id;

    @NotBlank(message = "Card number cannot be empty")
    @Size(min = 10, max = 10, message = "Card number must be 10 characters")
    @Pattern(regexp = "\\d+", message = "Card number must contain only digits")
    private String cardNumber;

    @NotNull(message = "Issue date cannot be null")
    private LocalDate issueDate;

    @NotNull(message = "Expiry date cannot be null")
    private LocalDate expiryDate;

    public MembershipCardDTO() {
    }

    public MembershipCardDTO(Long id, String cardNumber, LocalDate issueDate, LocalDate expiryDate) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
