package shopping.order.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.order.app.api.receipt.ReceiptUseCase;
import shopping.order.app.api.receipt.response.ReceiptDetailProductResponse;
import shopping.order.app.api.receipt.response.ReceiptDetailResponse;
import shopping.order.app.api.receipt.response.ReceiptProductResponse;
import shopping.order.app.api.receipt.response.ReceiptResponse;
import shopping.order.app.domain.Receipt;
import shopping.order.app.domain.ReceiptProduct;
import shopping.order.app.exception.DoesNotFindReceiptException;
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

    @Override
    public ReceiptDetailResponse getByIdAndUserId(long id, long userId) {
        Receipt receipt = receiptRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new DoesNotFindReceiptException(id));

        return toReceiptDetailResponse(receipt);
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

    private ReceiptDetailResponse toReceiptDetailResponse(Receipt receipt) {
        return new ReceiptDetailResponse(receipt.getId(), toReceiptDetailProductResponse(receipt.getReceiptProducts()),
                receipt.getTotalPrice().toString(), receipt.getExchangedPrice().toString(), receipt.getExchangeRate());
    }

    private List<ReceiptDetailProductResponse> toReceiptDetailProductResponse(List<ReceiptProduct> receiptProducts) {
        return receiptProducts.stream()
                .map(receiptProduct -> new ReceiptDetailProductResponse(receiptProduct.getProductId(),
                        receiptProduct.getName(), receiptProduct.getPrice().toString(), receiptProduct.getImageUrl(),
                        receiptProduct.getQuantity()))
                .collect(Collectors.toList());
    }

}
