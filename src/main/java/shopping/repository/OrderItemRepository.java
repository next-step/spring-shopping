package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrder(final Order order);
}
