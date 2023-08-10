package shopping.order.app.api.receipt;

import java.util.List;
import shopping.order.app.api.receipt.response.ReceiptDetailResponse;
import shopping.order.app.api.receipt.response.ReceiptResponse;

public interface ReceiptUseCase {

    List<ReceiptResponse> findAllByUserId(long userId);

    ReceiptDetailResponse getByIdAndUserId(long id, long userId);
}
