package shopping.mart.persist.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.core.entity.OrderEntity;
import shopping.core.entity.OrderProductEntity;

public interface OrderProductJpaRepository extends JpaRepository<OrderProductEntity, Long> {

    List<OrderProductEntity> findByOrderEntity(OrderEntity orderEntity);
    List<OrderProductEntity> findAllByOrderEntityIn(List<OrderEntity> orderEntities);
}
