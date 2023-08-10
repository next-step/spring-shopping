package shopping.order.domain.usecase.receipt.response;

import java.util.List;

public final class ReceiptResponse {

    private Long id;
    private List<ReceiptProductResponse> receiptProducts;

    public ReceiptResponse() {
    }

    public ReceiptResponse(Long id, List<ReceiptProductResponse> receiptProducts) {
        this.id = id;
        this.receiptProducts = receiptProducts;
    }

    public Long getId() {
        return id;
    }

    public List<ReceiptProductResponse> getReceiptProducts() {
        return receiptProducts;
    }
}
