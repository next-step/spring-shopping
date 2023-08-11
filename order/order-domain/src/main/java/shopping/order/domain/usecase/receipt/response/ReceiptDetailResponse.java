package shopping.order.domain.usecase.receipt.response;

import java.util.List;

public final class ReceiptDetailResponse {

    private Long id;
    private List<ReceiptDetailProductResponse> receiptDetailProducts;
    private String totalPrice;
    private String exchangedPrice;
    private double exchangeRate;

    public ReceiptDetailResponse() {
    }

    public ReceiptDetailResponse(Long id, List<ReceiptDetailProductResponse> receiptDetailProducts, String totalPrice,
            String exchangedPrice, double exchangeRate) {
        this.id = id;
        this.receiptDetailProducts = receiptDetailProducts;
        this.totalPrice = totalPrice;
        this.exchangedPrice = exchangedPrice;
        this.exchangeRate = exchangeRate;
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

    public double getExchangeRate() {
        return exchangeRate;
    }
}
