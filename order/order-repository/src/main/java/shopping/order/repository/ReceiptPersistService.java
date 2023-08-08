package shopping.order.repository;

import org.springframework.stereotype.Repository;
import shopping.order.app.domain.Receipt;
import shopping.order.app.spi.ReceiptRepository;
import shopping.order.repository.entity.ReceiptEntity;

@Repository
public class ReceiptPersistService implements ReceiptRepository {

    private final ReceiptJpaRepository receiptJpaRepository;

    public ReceiptPersistService(ReceiptJpaRepository receiptJpaRepository) {
        this.receiptJpaRepository = receiptJpaRepository;
    }

    @Override
    public void persist(Receipt receipt) {
        receiptJpaRepository.save(new ReceiptEntity(receipt));
    }
}
