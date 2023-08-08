package shopping.order.app.spi;

import java.util.List;
import java.util.Optional;
import shopping.order.app.domain.Receipt;

public interface ReceiptRepository {

    long persist(Receipt receipt);

    List<Receipt> findAllByUserId(long userId);

    Optional<Receipt> findByIdAndUserId(long id, long userId);
}
