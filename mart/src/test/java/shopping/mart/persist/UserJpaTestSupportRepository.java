package shopping.mart.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.auth.persist.entity.UserEntity;

public interface UserJpaTestSupportRepository extends JpaRepository<UserEntity, Long> {
}
