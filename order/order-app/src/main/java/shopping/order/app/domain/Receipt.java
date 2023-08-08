package shopping.order.app.domain;

import java.math.BigInteger;
import java.util.List;

public final class Receipt {

    private final Long id;
    private final long userId;
    private final List<ReceiptProduct> receiptProducts;
    private final BigInteger totalPrice;

    public Receipt(long userId, List<ReceiptProduct> receiptProducts, BigInteger totalPrice) {
        this(null, userId, receiptProducts, totalPrice);
    }

    public Receipt(Long id, long userId, List<ReceiptProduct> receiptProducts, BigInteger totalPrice) {
        this.id = id;
        this.userId = userId;
        this.receiptProducts = receiptProducts;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public List<ReceiptProduct> getReceiptProducts() {
        return receiptProducts;
    }

    public BigInteger getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", userId=" + userId +
                ", receiptProducts=" + receiptProducts +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
