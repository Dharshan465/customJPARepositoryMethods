package ford.relationalMapping.assignment19.LibraryManagementSystem.controller;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BorrowedBookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MemberCreationDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MemberDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDetailDTO> registerMember(@Valid @RequestBody MemberCreationDTO memberCreationDTO) {
        MemberDetailDTO newMember = memberService.registerMember(memberCreationDTO);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDetailDTO> getMemberWithCard(@PathVariable Long id) {
        MemberDetailDTO member = memberService.getMemberWithCard(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{memberId}/books")
    public ResponseEntity<List<BorrowedBookDTO>> getBorrowedBooksByMember(@PathVariable Long memberId) {
        List<BorrowedBookDTO> borrowedBooks = memberService.getBorrowedBooksByMember(memberId);
        return ResponseEntity.ok(borrowedBooks);
    }

    @PostMapping("/{memberId}/borrow/{bookId}")
    public ResponseEntity<Void> borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        memberService.borrowBook(memberId, bookId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}/return/{bookId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        memberService.returnBook(memberId, bookId);
        return ResponseEntity.ok().build();
    }
}
