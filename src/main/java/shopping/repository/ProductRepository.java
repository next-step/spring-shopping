package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.domain.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
