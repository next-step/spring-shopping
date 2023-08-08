package shopping.order.app.api.receipt;

import java.util.List;
import shopping.order.app.api.receipt.response.ReceiptDetailResponse;

public interface ReceiptUseCase {

    List<ReceiptDetailResponse> findAllByUserId(long userId);
}
