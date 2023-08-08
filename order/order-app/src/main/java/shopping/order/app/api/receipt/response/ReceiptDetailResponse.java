package shopping.order.app.api.receipt.response;

import java.util.List;

public final class ReceiptDetailResponse {

    private Long id;
    private List<ReceiptProductResponse> receiptProducts;
    private String totalPrice;

    public ReceiptDetailResponse() {
    }

    public ReceiptDetailResponse(Long id, List<ReceiptProductResponse> receiptProducts, String totalPrice) {
        this.id = id;
        this.receiptProducts = receiptProducts;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public List<ReceiptProductResponse> getReceiptProducts() {
        return receiptProducts;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}
