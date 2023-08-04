package shopping.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.cart.domain.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
