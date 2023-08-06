package shopping.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.auth.repository.entity.UserEntity;

interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
