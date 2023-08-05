package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
