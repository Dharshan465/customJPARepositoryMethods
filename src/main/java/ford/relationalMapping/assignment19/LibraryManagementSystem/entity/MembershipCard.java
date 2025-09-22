package ford.relationalMapping.assignment19.LibraryManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "membership_cards")
public class MembershipCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Card number cannot be empty")
    @Size(min = 10, max = 10, message = "Card number must be 10 characters")
    @Column(nullable = false, unique = true, length = 10)
    private String cardNumber;

    @NotNull(message = "Issue date cannot be null")
    @Column(nullable = false)
    @PastOrPresent(message = "Issue date cannot be in the future")
    private LocalDate issueDate;

    @NotNull(message = "Expiry date cannot be null")
    @Column(nullable = false)
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    // One-to-One with Member (inverse side)
    @OneToOne(mappedBy = "membershipCard", fetch = FetchType.LAZY)
    @JsonIgnore // Prevent infinite recursion
    private Member member;

    public MembershipCard() {
    }

    public MembershipCard(String cardNumber, LocalDate issueDate, LocalDate expiryDate) {
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
