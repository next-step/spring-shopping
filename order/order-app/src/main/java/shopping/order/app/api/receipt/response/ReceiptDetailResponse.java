package shopping.order.app.api.receipt.response;

import java.util.List;

public final class ReceiptDetailResponse {

    private Long id;
    private List<ReceiptDetailProductResponse> receiptDetailProducts;
    private String totalPrice;

    public ReceiptDetailResponse() {
    }

    public ReceiptDetailResponse(Long id, List<ReceiptDetailProductResponse> receiptDetailProducts, String totalPrice) {
        this.id = id;
        this.receiptDetailProducts = receiptDetailProducts;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public List<ReceiptDetailProductResponse> getReceiptDetailProducts() {
        return receiptDetailProducts;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}
