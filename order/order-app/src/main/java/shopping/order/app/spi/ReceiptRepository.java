package shopping.order.app.spi;

import java.util.List;
import shopping.order.app.domain.Receipt;

public interface ReceiptRepository {

    void persist(Receipt receipt);

    List<Receipt> findAllByUserId(long userId);
}
