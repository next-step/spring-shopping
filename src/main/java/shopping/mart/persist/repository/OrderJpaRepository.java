package shopping.mart.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.core.entity.OrderEntity;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
