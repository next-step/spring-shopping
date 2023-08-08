package shopping.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.cart.domain.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}