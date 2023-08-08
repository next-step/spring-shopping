package shopping.order.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.order.app.api.receipt.ReceiptUseCase;
import shopping.order.app.api.receipt.response.ReceiptProductResponse;
import shopping.order.app.api.receipt.response.ReceiptResponse;
import shopping.order.app.domain.Receipt;
import shopping.order.app.domain.ReceiptProduct;
import shopping.order.app.spi.ReceiptRepository;

@Service
@Transactional(readOnly = true)
public class ReceiptService implements ReceiptUseCase {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public List<ReceiptResponse> findAllByUserId(long userId) {
        List<Receipt> receipts = receiptRepository.findAllByUserId(userId);

        return toReceiptResponse(receipts);
    }

    private List<ReceiptResponse> toReceiptResponse(List<Receipt> receipts) {
        return receipts.stream()
                .map(receipt -> new ReceiptResponse(receipt.getId(),
                        toReceiptProductResponse(receipt.getReceiptProducts())))
                .collect(Collectors.toList());
    }

    private List<ReceiptProductResponse> toReceiptProductResponse(List<ReceiptProduct> receiptProducts) {
        return receiptProducts.stream()
                .map(receiptProduct -> new ReceiptProductResponse(receiptProduct.getProductId(),
                        receiptProduct.getName(), receiptProduct.getPrice().toString(), receiptProduct.getImageUrl(),
                        receiptProduct.getQuantity()))
                .collect(Collectors.toList());
    }
}
