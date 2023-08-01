package shopping.persist.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.persist.entity.CartEntity;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserId(long userId);

    boolean existsByUserId(long userId);
}
