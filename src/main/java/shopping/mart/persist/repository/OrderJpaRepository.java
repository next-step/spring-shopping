package shopping.mart.persist.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.core.entity.OrderEntity;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByIdAndUserId(Long orderId, Long userId);
    List<OrderEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
