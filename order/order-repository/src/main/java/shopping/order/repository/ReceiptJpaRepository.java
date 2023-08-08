package shopping.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.order.repository.entity.ReceiptEntity;

public interface ReceiptJpaRepository extends JpaRepository<ReceiptEntity, Long> {
}
