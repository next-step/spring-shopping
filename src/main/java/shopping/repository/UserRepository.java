package shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.entity.User;
import shopping.domain.vo.Email;
import shopping.domain.vo.Password;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(final Email email, final Password password);
}
