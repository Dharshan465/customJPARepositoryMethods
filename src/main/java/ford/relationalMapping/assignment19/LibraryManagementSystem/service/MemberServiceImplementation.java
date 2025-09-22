package ford.relationalMapping.assignment19.LibraryManagementSystem.service;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.AuthorDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.BorrowedBookDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MemberCreationDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MemberDetailDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.MembershipCardDTO;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Book;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Member;
import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.MembershipCard;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.BookNotFoundException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.BorrowingLimitExceededException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.DuplicateBorrowException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.ExpiredMembershipException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.InvalidCardException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.exception.MemberNotFoundException;
import ford.relationalMapping.assignment19.LibraryManagementSystem.repository.BookRepository;
import ford.relationalMapping.assignment19.LibraryManagementSystem.repository.MemberRepository;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImplementation implements MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public MemberServiceImplementation(MemberRepository memberRepository, BookRepository bookRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public MemberDetailDTO registerMember(MemberCreationDTO memberCreationDTO) {
        if (memberCreationDTO.getMembershipCard().getExpiryDate().isBefore(LocalDate.now())) {
            throw new InvalidCardException("Membership card expiry date cannot be in the past.");
        }

        Member member = new Member();
        member.setName(memberCreationDTO.getName());
        member.setEmail(memberCreationDTO.getEmail());

        MembershipCard membershipCard = new MembershipCard();
        membershipCard.setCardNumber(memberCreationDTO.getMembershipCard().getCardNumber());
        membershipCard.setIssueDate(memberCreationDTO.getMembershipCard().getIssueDate());
        membershipCard.setExpiryDate(memberCreationDTO.getMembershipCard().getExpiryDate());

        member.setMembershipCard(membershipCard); // This sets the bidirectional relationship

        Member savedMember = memberRepository.save(member);

        return mapToMemberDetailDTO(savedMember);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDetailDTO getMemberWithCard(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + memberId));

        // Accessing membershipCard and borrowedBooks triggers lazy loading within the transaction
        return mapToMemberDetailDTO(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowedBookDTO> getBorrowedBooksByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + memberId));

        // Accessing borrowedBooks triggers lazy loading
        return member.getBorrowedBooks().stream()
                .map(book -> new BorrowedBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName(), book.getAuthor().getNationality())
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));

        // Business rules
        if (member.getMembershipCard().getExpiryDate().isBefore(LocalDate.now())) {
            throw new ExpiredMembershipException("Member's card has expired. Cannot borrow books.");
        }
        if (book.getBorrowedByMembers().size() >= 5) {
            throw new BorrowingLimitExceededException("This book is already borrowed by 5 members and cannot be borrowed further.");
        }
        if (member.getBorrowedBooks().contains(book)) {
            throw new DuplicateBorrowException("Member has already borrowed this book.");
        }

        member.borrowBook(book);
        memberRepository.save(member); // Save changes to the owning side
    }

    @Override
    @Transactional
    public void returnBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));

        if (!member.getBorrowedBooks().contains(book)) {
            throw new DuplicateBorrowException("Member did not borrow this book."); // Reusing for "not borrowed"
        }

        member.returnBook(book);
        memberRepository.save(member); // Save changes to the owning side
    }

    private MemberDetailDTO mapToMemberDetailDTO(Member member) {
        MembershipCardDTO cardDTO = new MembershipCardDTO(
                member.getMembershipCard().getId(),
                member.getMembershipCard().getCardNumber(),
                member.getMembershipCard().getIssueDate(),
                member.getMembershipCard().getExpiryDate()
        );

        List<BorrowedBookDTO> borrowedBooksDTO = member.getBorrowedBooks().stream()
                .map(book -> new BorrowedBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName(), book.getAuthor().getNationality())
                ))
                .collect(Collectors.toList());

        return new MemberDetailDTO(member.getId(), member.getName(), member.getEmail(), cardDTO, borrowedBooksDTO);
    }
}
