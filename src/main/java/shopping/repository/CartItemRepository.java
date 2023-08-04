package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping.domain.cart.CartItem;
import shopping.domain.user.Email;
import shopping.domain.cart.Product;
import shopping.domain.user.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    @Query("SELECT ci, u, p FROM CartItem ci INNER JOIN User u ON ci.user.id = u.id INNER JOIN Product p ON ci.product.id = p.id WHERE u.email = :email")
    List<CartItem> findAllByUserEmail(@Param("email") Email email);
}
