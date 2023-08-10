package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Product;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserIdAndProduct(Long userId, Product product);

    List<CartItem> findAllByUserId(@Param("userId") Long userId);
}
