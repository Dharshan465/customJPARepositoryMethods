package ford.relationalMapping.assignment19.LibraryManagementSystem.dto;

import java.util.List;

public class MemberDetailDTO {
    private Long id;
    private String name;
    private String email;
    private MembershipCardDTO membershipCard;
    private List<BorrowedBookDTO> borrowedBooks; // Simplified book info

    public MemberDetailDTO() {
    }

    public MemberDetailDTO(Long id, String name, String email, MembershipCardDTO membershipCard, List<BorrowedBookDTO> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.membershipCard = membershipCard;
        this.borrowedBooks = borrowedBooks;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MembershipCardDTO getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCardDTO membershipCard) {
        this.membershipCard = membershipCard;
    }

    public List<BorrowedBookDTO> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBookDTO> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
