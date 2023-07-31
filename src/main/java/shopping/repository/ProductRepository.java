package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
