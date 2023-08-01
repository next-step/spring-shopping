package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.domain.CartItem;

@Repository
public interface CartItemRespository extends JpaRepository<CartItem, Long> {
}
