package shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.entity.user.Email;
import shopping.entity.user.Password;
import shopping.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(final Email email, final Password password);
}
