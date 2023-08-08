package shopping.order.app.spi;

import shopping.order.app.domain.Order;

public interface ReceiptRepository {

    void persist(Order order);
}
