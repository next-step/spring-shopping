package shopping.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}
