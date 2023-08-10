package shopping.auth.app.spi;

import java.util.Optional;
import shopping.auth.app.domain.User;

public interface UserRepository {

    Optional<User> findByEmail(final String email);

    void saveUser(User user);
}
