package shopping.order.app.spi;

import shopping.order.app.domain.Receipt;

public interface ReceiptRepository {

    void persist(Receipt receipt);
}
