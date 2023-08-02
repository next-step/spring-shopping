package shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.Email;
import shopping.domain.Password;
import shopping.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(Email email, Password password);
}
