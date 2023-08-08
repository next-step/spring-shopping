package shopping.order.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.order.repository.entity.ReceiptEntity;

public interface ReceiptJpaRepository extends JpaRepository<ReceiptEntity, Long> {

    List<ReceiptEntity> findAllByUserId(Long userId);

    ReceiptEntity getReferenceByIdAndUserId(long id, long userId);

    Optional<ReceiptEntity> findByIdAndUserId(long id, long userId);

}
