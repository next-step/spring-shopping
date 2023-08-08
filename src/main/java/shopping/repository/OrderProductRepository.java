package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.entity.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {

}
