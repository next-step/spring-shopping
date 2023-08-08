package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

}
