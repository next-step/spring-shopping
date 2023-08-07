package shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(final String email, final String password);
}
