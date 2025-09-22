package ford.relationalMapping.assignment19.LibraryManagementSystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MemberCreationDTO {
    @NotBlank(message = "Member name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Membership card details cannot be null")
    @Valid // Validate nested MembershipCardDTO
    private MembershipCardDTO membershipCard;

    public MemberCreationDTO() {
    }

    public MemberCreationDTO(String name, String email, MembershipCardDTO membershipCard) {
        this.name = name;
        this.email = email;
        this.membershipCard = membershipCard;
    }

    // Getters and Setters
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
}
