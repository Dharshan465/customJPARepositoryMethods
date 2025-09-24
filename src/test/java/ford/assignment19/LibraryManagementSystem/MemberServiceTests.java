package ford.assignment19.LibraryManagementSystem;

import ford.relationalMapping.assignment19.LibraryManagementSystem.LibraryManagementSystemApplication;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MemberCreationDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MemberDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MembershipCardDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Member;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.MembershipCard;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.InvalidCardException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.repository.MemberRepository;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest(classes = LibraryManagementSystemApplication.class)
public class MemberServiceTests {
    @MockitoBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;
    @Test
    void registerMember_SuccessTest() {
        // 1. Prepare the input DTO with a valid expiry date
        LocalDate issueDate = LocalDate.now();
        LocalDate expiryDate = issueDate.plusYears(1); // Valid expiry date
        MembershipCardDTO cardDTO = new MembershipCardDTO(null, "1234567890", issueDate, expiryDate);
        MemberCreationDTO memberCreationDTO = new MemberCreationDTO("Dharshan", "d@ford.com", cardDTO);

        // 2. Prepare the expected Member entity that the mocked repository's save() method
        //    should return. This simulates the database assigning an ID.
        MembershipCard savedCardEntity = new MembershipCard("1234567890", issueDate, expiryDate);
        savedCardEntity.setId(101L); // Simulate ID assigned by DB for the card

        Member savedMemberEntity = new Member("Dharshan", "d@ford.com");
        savedMemberEntity.setId(1L); // Simulate ID assigned by DB for the member
        savedMemberEntity.setMembershipCard(savedCardEntity);
        savedCardEntity.setMember(savedMemberEntity); // Ensure bidirectional link is set for consistency

        // 3. Stub the behavior of the mocked memberRepository
        //    When save is called with *any* Member object, return our pre-defined savedMemberEntity.
        Mockito.when(memberRepository.save(any(Member.class))).thenReturn(savedMemberEntity);

        // 4. Call the service method we are testing
        MemberDetailDTO resultDTO = memberService.registerMember(memberCreationDTO);

        // 5. Assert the results returned by the service
        Assertions.assertNotNull(resultDTO);
        Assertions.assertEquals(1L, resultDTO.getId());
        Assertions.assertEquals("Dharshan", resultDTO.getName());
        Assertions.assertEquals("d@ford.com", resultDTO.getEmail());
        Assertions.assertNotNull(resultDTO.getMembershipCard());
        Assertions.assertEquals(101L, resultDTO.getMembershipCard().getId());
        Assertions.assertEquals("1234567890", resultDTO.getMembershipCard().getCardNumber());
        Assertions.assertEquals(issueDate, resultDTO.getMembershipCard().getIssueDate());
        Assertions.assertEquals(expiryDate, resultDTO.getMembershipCard().getExpiryDate());

        // Optional: Verify that the mocked save method was called exactly once
        Mockito.verify(memberRepository, Mockito.times(1)).save(any(Member.class));
        // No verify for findByEmail as the service doesn't call it
    }

    @Test
    void registerMember_InvalidExpiryDate_ThrowsException() {
        // 1. Prepare input DTO with an expiry date in the past
        LocalDate issueDate = LocalDate.now().minusYears(2);
        LocalDate expiryDate = LocalDate.now().minusYears(1); // Expiry date in the past
        MembershipCardDTO cardDTO = new MembershipCardDTO(null, "1234567890", issueDate, expiryDate);
        MemberCreationDTO memberCreationDTO = new MemberCreationDTO("Dharshan", "d@ford.com", cardDTO);

        // 2. No need to stub memberRepository.save() as it should not be called

        // 3. Call the service method and assert that the InvalidCardException is thrown
        InvalidCardException thrown = Assertions.assertThrows(
                InvalidCardException.class,
                () -> memberService.registerMember(memberCreationDTO)
        );

        Assertions.assertEquals("Membership card expiry date cannot be in the past.", thrown.getMessage());

        // Optional: Verify that memberRepository.save() was NOT called
        Mockito.verify(memberRepository, Mockito.never()).save(any(Member.class));
    }



}


