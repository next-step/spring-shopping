package shopping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Long orderId);
}
