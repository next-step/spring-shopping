package shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.user.Email;
import shopping.domain.user.Password;
import shopping.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(final Email email, final Password password);
}
