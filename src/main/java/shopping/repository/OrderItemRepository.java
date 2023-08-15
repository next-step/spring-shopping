package shopping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.Order;
import shopping.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);
}
