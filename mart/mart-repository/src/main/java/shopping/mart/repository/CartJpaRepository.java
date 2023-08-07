package shopping.mart.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.mart.repository.entity.CartEntity;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserId(long userId);

    boolean existsByUserId(long userId);

    CartEntity getReferenceByUserId(long userId);
}
