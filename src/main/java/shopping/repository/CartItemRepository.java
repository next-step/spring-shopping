package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.CartItem;
import shopping.domain.Email;
import shopping.domain.Product;
import shopping.domain.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    // TODO : 쿼리 최적화
    List<CartItem> findAllByUserEmail(Email email);
}