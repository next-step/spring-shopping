package shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Product;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserIdAndProduct(Long userId, Product product);

    //@Query("SELECT ci, p FROM CartItem ci INNER JOIN Product p ON ci.product.id = p.id WHERE ci.userId = :userId")
    Page<CartItem> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
