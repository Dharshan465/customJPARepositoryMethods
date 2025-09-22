package ford.relationalMapping.assignment19.LibraryManagementSystem;

import ford.relationalMapping.assignment19.LibraryManagementSystem.dto.*;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.AuthorService;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.BookService;
import ford.relationalMapping.assignment19.LibraryManagementSystem.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class LibraryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(MemberService memberService,
                                 AuthorService authorService,
                                 BookService bookService) {
        return args -> {
            System.out.println("--- Library Management System Sample Data & Demonstrations ---");

            // Phase 1: Member & MembershipCard (One-to-One)
            System.out.println("\n--- Phase 1: Member & MembershipCard ---");

            // Register Member 1 (Valid card)
            MembershipCardDTO card1 = new MembershipCardDTO(null, "CARD000001", LocalDate.now(), LocalDate.now().plusYears(1));
            MemberCreationDTO member1Data = new MemberCreationDTO("Alice Smith", "alice@example.com", card1);
            MemberDetailDTO member1 = memberService.registerMember(member1Data);
            System.out.println("Registered Member 1: " + member1.getName() + " with Card No: " + member1.getMembershipCard().getCardNumber() + " (ID: " + member1.getId() + ")");

            // Register Member 2 (Expired card - should throw InvalidCardException if uncommented)
            try {
                MembershipCardDTO card2Expired = new MembershipCardDTO(null, "CARD000002", LocalDate.now().minusYears(2), LocalDate.now().minusYears(1));
                MemberCreationDTO member2ExpiredData = new MemberCreationDTO("Bob Johnson", "bob@example.com", card2Expired);
                // MemberDetailDTO member2Expired = memberService.registerMember(member2ExpiredData);
                // System.out.println("Registered Member 2 (Expired): " + member2Expired.getName());
                System.out.println("Attempted to register Bob Johnson with expired card - expected to fail.");
            } catch (Exception e) {
                System.out.println("  Caught expected exception: " + e.getMessage());
            }

            // Register Member 3 (Valid card, for borrowing demo)
            MembershipCardDTO card3 = new MembershipCardDTO(null, "CARD000003", LocalDate.now(), LocalDate.now().plusMonths(6));
            MemberCreationDTO member3Data = new MemberCreationDTO("Charlie Brown", "charlie@example.com", card3);
            MemberDetailDTO member3 = memberService.registerMember(member3Data);
            System.out.println("Registered Member 3: " + member3.getName() + " (ID: " + member3.getId() + ")");

            // Get Member 1 with card details (triggers lazy loading of card)
            MemberDetailDTO fetchedMember1 = memberService.getMemberWithCard(member1.getId());
            System.out.println("Fetched Member 1: " + fetchedMember1.getName() + ", Card Expiry: " + fetchedMember1.getMembershipCard().getExpiryDate());

            // Phase 2: Author & Books (One-to-Many)
            System.out.println("\n--- Phase 2: Author & Books ---");

            // Add Author 1
            AuthorDTO author1Data = new AuthorDTO(null, "Stephen King", "American");
            AuthorDTO author1 = authorService.addAuthor(author1Data);
            System.out.println("Added Author: " + author1.getName() + " (ID: " + author1.getId() + ")");

            // Add Author 2
            AuthorDTO author2Data = new AuthorDTO(null, "J.K. Rowling", "British");
            AuthorDTO author2 = authorService.addAuthor(author2Data);
            System.out.println("Added Author: " + author2.getName() + " (ID: " + author2.getId() + ")");

            // Add Book 1 for Author 1
            BookCreationDTO book1Data = new BookCreationDTO("It", "978-0-451-16951-8");
            BookDTO book1 = authorService.addBookForAuthor(author1.getId(), book1Data);
            System.out.println("Added Book: '" + book1.getTitle() + "' by " + book1.getAuthor().getName() + " (ID: " + book1.getId() + ")");

            // Add Book 2 for Author 1
            BookCreationDTO book2Data = new BookCreationDTO("The Shining", "978-0-307-74365-0");
            BookDTO book2 = authorService.addBookForAuthor(author1.getId(), book2Data);
            System.out.println("Added Book: '" + book2.getTitle() + "' by " + book2.getAuthor().getName() + " (ID: " + book2.getId() + ")");

            // Add Book 3 for Author 2
            BookCreationDTO book3Data = new BookCreationDTO("Harry Potter and the Sorcerer's Stone", "978-0-590-35342-7");
            BookDTO book3 = authorService.addBookForAuthor(author2.getId(), book3Data);
            System.out.println("Added Book: '" + book3.getTitle() + "' by " + book3.getAuthor().getName() + " (ID: " + book3.getId() + ")");

            // Attempt to add book with existing ISBN (should throw BookAlreadyExistsException)
            try {
                BookCreationDTO duplicateBookData = new BookCreationDTO("Another It", "978-0-451-16951-8");
                // authorService.addBookForAuthor(author1.getId(), duplicateBookData);
                System.out.println("Attempted to add duplicate book - expected to fail.");
            } catch (Exception e) {
                System.out.println("  Caught expected exception: " + e.getMessage());
            }

            // Get all books by Author 1 (triggers lazy loading of books)
            AuthorDetailDTO fetchedAuthor1 = authorService.getAuthorWithBooks(author1.getId());
            System.out.println("Books by " + fetchedAuthor1.getName() + ":");
            fetchedAuthor1.getBooks().forEach(b -> System.out.println("  - " + b.getTitle()));

            // Phase 3: Members & Books (Many-to-Many Borrowing)
            System.out.println("\n--- Phase 3: Members & Books (Borrowing) ---");

            // Borrow Book 1 by Member 1
            try {
                memberService.borrowBook(member1.getId(), book1.getId());
                System.out.println(member1.getName() + " borrowed '" + book1.getTitle() + "'");
            } catch (Exception e) {
                System.out.println("  Error borrowing: " + e.getMessage());
            }

            // Borrow Book 2 by Member 1
            try {
                memberService.borrowBook(member1.getId(), book2.getId());
                System.out.println(member1.getName() + " borrowed '" + book2.getTitle() + "'");
            } catch (Exception e) {
                System.out.println("  Error borrowing: " + e.getMessage());
            }

            // Borrow Book 1 by Member 3
            try {
                memberService.borrowBook(member3.getId(), book1.getId());
                System.out.println(member3.getName() + " borrowed '" + book1.getTitle() + "'");
            } catch (Exception e) {
                System.out.println("  Error borrowing: " + e.getMessage());
            }

            // Attempt to borrow same book twice by Member 1 (should throw DuplicateBorrowException)
            try {
                memberService.borrowBook(member1.getId(), book1.getId());
                System.out.println("Attempted " + member1.getName() + " to borrow '" + book1.getTitle() + "' again - expected to fail.");
            } catch (Exception e) {
                System.out.println("  Caught expected exception: " + e.getMessage());
            }

            // Demonstrate expired card borrowing (Member with expired card is not created, so use a non-existent ID for demo)
            try {
                System.out.println("Attempting to borrow with an expired card (simulated via non-existent member) - expected to fail.");
                memberService.borrowBook(999L, book3.getId()); // Use a non-existent member ID
            } catch (Exception e) {
                System.out.println("  Caught expected exception: " + e.getMessage());
            }

            // List borrowed books of Member 1
            List<BorrowedBookDTO> member1Borrowed = memberService.getBorrowedBooksByMember(member1.getId());
            System.out.println("\n" + member1.getName() + "'s borrowed books:");
            member1Borrowed.forEach(b -> System.out.println("  - " + b.getTitle()));

            // List members who borrowed Book 1
            BookDetailDTO book1Borrowers = bookService.getBookWithBorrowers(book1.getId());
            System.out.println("\nMembers who borrowed '" + book1Borrowers.getTitle() + "':");
            book1Borrowers.getBorrowedByMembers().forEach(m -> System.out.println("  - " + m.getName()));

            // Return Book 1 by Member 1
            try {
                memberService.returnBook(member1.getId(), book1.getId());
                System.out.println(member1.getName() + " returned '" + book1.getTitle() + "'");
            } catch (Exception e) {
                System.out.println("  Error returning: " + e.getMessage());
            }

            // List borrowed books of Member 1 after return
            member1Borrowed = memberService.getBorrowedBooksByMember(member1.getId());
            System.out.println("\n" + member1.getName() + "'s borrowed books after return:");
            if (member1Borrowed.isEmpty()) {
                System.out.println("  No books currently borrowed.");
            } else {
                member1Borrowed.forEach(b -> System.out.println("  - " + b.getTitle()));
            }

            // Demonstrate BorrowingLimitExceededException
            System.out.println("\n--- Demonstrating Borrowing Limit Exceeded ---");
            // Create 5 more members and have them borrow book3
            Long book3Id = book3.getId();
            for (int i = 4; i <= 8; i++) {
                MembershipCardDTO card = new MembershipCardDTO(null, "CARD00000" + i, LocalDate.now(), LocalDate.now().plusMonths(3));
                MemberCreationDTO newMemberData = new MemberCreationDTO("Member " + i, "member" + i + "@example.com", card);
                MemberDetailDTO newMember = memberService.registerMember(newMemberData);
                try {
                    memberService.borrowBook(newMember.getId(), book3Id);
                    System.out.println("Member " + i + " borrowed '" + book3.getTitle() + "'");
                } catch (Exception e) {
                    System.out.println("  Error (Member " + i + "): " + e.getMessage());
                }
            }

            // Attempt to borrow book3 by a 6th member (should fail)
            MembershipCardDTO card9 = new MembershipCardDTO(null, "CARD000009", LocalDate.now(), LocalDate.now().plusMonths(3));
            MemberCreationDTO member9Data = new MemberCreationDTO("Member 9", "member9@example.com", card9);
            MemberDetailDTO member9 = memberService.registerMember(member9Data);
            try {
                System.out.println("\nAttempting Member 9 to borrow '" + book3.getTitle() + "' (should exceed limit) - expected to fail.");
                memberService.borrowBook(member9.getId(), book3Id);
            } catch (Exception e) {
                System.out.println("  Caught expected exception: " + e.getMessage());
            }

            System.out.println("\n--- All demonstrations complete ---");
        };
    }
}
