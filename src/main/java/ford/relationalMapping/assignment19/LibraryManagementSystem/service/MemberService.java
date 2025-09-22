package ford.relationalMapping.assignment19.LibraryManagementSystem.service;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BorrowedBookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MemberCreationDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MemberDetailDTO;

import java.util.List;

public interface MemberService {
    MemberDetailDTO registerMember(MemberCreationDTO memberCreationDTO);
    MemberDetailDTO getMemberWithCard(Long memberId);
    List<BorrowedBookDTO> getBorrowedBooksByMember(Long memberId);
    void borrowBook(Long memberId, Long bookId);
    void returnBook(Long memberId, Long bookId);
}
