package shopping.order.app.domain;

import java.math.BigInteger;
import java.util.List;

public final class Receipt {

    private final Long id;
    private final List<ReceiptProduct> receiptProducts;
    private final BigInteger totalPrice;

    public Receipt(List<ReceiptProduct> receiptProducts, BigInteger totalPrice) {
        this(null, receiptProducts, totalPrice);
    }

    public Receipt(Long id, List<ReceiptProduct> receiptProducts, BigInteger totalPrice) {
        this.id = id;
        this.receiptProducts = receiptProducts;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public List<ReceiptProduct> getReceiptProducts() {
        return receiptProducts;
    }

    public BigInteger getTotalPrice() {
        return totalPrice;
    }
}
