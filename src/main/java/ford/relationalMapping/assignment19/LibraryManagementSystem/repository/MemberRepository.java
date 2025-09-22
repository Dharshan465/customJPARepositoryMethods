package ford.relationalMapping.assignment19.LibraryManagementSystem.repository;

import ford.relationalMapping.assignment19.LibraryManagementSystem.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
