package shopping.mart.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.core.entity.UserEntity;

public interface UserJpaTestSupportRepository extends JpaRepository<UserEntity, Long> {
}
