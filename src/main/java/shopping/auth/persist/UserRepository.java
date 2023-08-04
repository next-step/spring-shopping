package shopping.auth.persist;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import shopping.auth.domain.User;
import shopping.auth.persist.entity.UserEntity;
import shopping.auth.persist.repository.UserJpaRepository;

@Repository
public class UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public Optional<User> findByEmail(final String email) {
        Optional<UserEntity> optionalUserEntity = userJpaRepository.findByEmail(email);
        return optionalUserEntity.map(
            userEntity -> new User(userEntity.getId(), userEntity.getEmail(),
                userEntity.getPassword()));
    }

    public void saveUser(User user) {
        UserEntity userEntity = new UserEntity(user.getId(), user.getEmail(), user.getPassword());
        userJpaRepository.save(userEntity);
    }
}
