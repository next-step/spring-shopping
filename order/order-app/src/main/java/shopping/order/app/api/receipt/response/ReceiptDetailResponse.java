package shopping.order.app.api.receipt.response;

import java.util.List;

public final class ReceiptDetailResponse {

    private Long id;
    private List<ReceiptDetailProductResponse> receiptDetailProducts;
    private String totalPrice;
    private String exchangedPrice;

    public ReceiptDetailResponse() {
    }

    public ReceiptDetailResponse(Long id, List<ReceiptDetailProductResponse> receiptDetailProducts, String totalPrice, String exchangedPrice) {
        this.id = id;
        this.receiptDetailProducts = receiptDetailProducts;
        this.totalPrice = totalPrice;
        this.exchangedPrice = exchangedPrice;
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

    public String getExchangedPrice() {
        return exchangedPrice;
    }

}
