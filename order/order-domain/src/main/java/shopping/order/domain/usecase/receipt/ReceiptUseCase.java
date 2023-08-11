package shopping.order.domain.usecase.receipt;

import java.util.List;
import shopping.order.domain.usecase.receipt.response.ReceiptDetailResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptResponse;

public interface ReceiptUseCase {

    List<ReceiptResponse> findAllByUserId(long userId);

    ReceiptDetailResponse getByIdAndUserId(long id, long userId);
}
