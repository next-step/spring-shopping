package shopping.repository;

import org.springframework.stereotype.Repository;
import shopping.domain.Member;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final EntityManager entityManager;

    public MemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Member> findByEmail(String email) {
        return entityManager.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultStream()
                .findAny();
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }
}
