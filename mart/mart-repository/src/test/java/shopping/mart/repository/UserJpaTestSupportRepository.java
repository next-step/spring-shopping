package shopping.mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.auth.repository.entity.UserEntity;

public interface UserJpaTestSupportRepository extends JpaRepository<UserEntity, Long> {
}
