package shopping.mart.persist.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.core.entity.ProductEntity;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);
}
