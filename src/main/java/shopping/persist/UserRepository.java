package shopping.persist;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import shopping.domain.User;

@Repository
public class UserRepository {

    public Optional<User> findByEmail(final String email) {
        return Optional.empty();
    }

    public void saveUser(User user) {
    }
}
