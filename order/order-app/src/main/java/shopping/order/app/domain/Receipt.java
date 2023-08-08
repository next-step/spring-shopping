package shopping.order.app.domain;

import java.math.BigInteger;
import java.util.List;

public final class Receipt {

    private final Long receiptId;
    private final List<ReceiptProduct> receiptProducts;
    private final BigInteger totalPrice;

    public Receipt(List<ReceiptProduct> receiptProducts, BigInteger totalPrice) {
        this(null, receiptProducts, totalPrice);
    }

    public Receipt(Long receiptId, List<ReceiptProduct> receiptProducts, BigInteger totalPrice) {
        this.receiptId = receiptId;
        this.receiptProducts = receiptProducts;
        this.totalPrice = totalPrice;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public List<ReceiptProduct> getReceiptProducts() {
        return receiptProducts;
    }

    public BigInteger getTotalPrice() {
        return totalPrice;
    }
}
