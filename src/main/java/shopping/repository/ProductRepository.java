package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
