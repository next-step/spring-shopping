package shopping.auth.domain.repository;

import java.util.Optional;
import shopping.auth.domain.User;

public interface UserRepository {

    Optional<User> findByEmail(final String email);

    void saveUser(User user);
}
