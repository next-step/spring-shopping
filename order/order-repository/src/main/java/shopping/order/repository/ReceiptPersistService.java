package shopping.order.repository;

import org.springframework.stereotype.Repository;
import shopping.order.app.domain.Receipt;
import shopping.order.app.spi.ReceiptRepository;

@Repository
public class ReceiptPersistService implements ReceiptRepository {

    @Override
    public void persist(Receipt receipt) {

    }
}
