package shopping.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);
}
