package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
