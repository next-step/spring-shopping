package shopping.auth.persist.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.core.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
