package shopping.auth.service.spi;

import java.util.Optional;
import shopping.auth.domain.User;

public interface UserRepository {

    Optional<User> findByEmail(final String email);

    void saveUser(User user);
}
