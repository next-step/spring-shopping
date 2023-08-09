package shopping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserId(Long userId);

}
