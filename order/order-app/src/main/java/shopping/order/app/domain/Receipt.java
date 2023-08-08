package shopping.order.app.domain;

import java.math.BigInteger;
import java.util.List;

public final class Receipt {

    private final long orderId;
    private final List<ReceiptProduct> receiptProducts;
    private final BigInteger totalPrice;

    public Receipt(long orderId, List<ReceiptProduct> receiptProducts, BigInteger totalPrice) {
        this.orderId = orderId;
        this.receiptProducts = receiptProducts;
        this.totalPrice = totalPrice;
    }

    public long getOrderId() {
        return orderId;
    }

    public List<ReceiptProduct> getReceiptProducts() {
        return receiptProducts;
    }

    public BigInteger getTotalPrice() {
        return totalPrice;
    }
}
