package shopping.order.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public final class Receipt {

    private final Long id;
    private final long userId;
    private final List<ReceiptProduct> receiptProducts;
    private final BigInteger totalPrice;
    private final BigDecimal exchangedPrice;
    private final double exchangeRate;

    public Receipt(long userId, List<ReceiptProduct> receiptProducts, BigInteger totalPrice, BigDecimal exchangedPrice, double exchageRate) {
        this(null, userId, receiptProducts, totalPrice, exchangedPrice, exchageRate);
    }

    public Receipt(Long id, long userId, List<ReceiptProduct> receiptProducts, BigInteger totalPrice, BigDecimal exchangedPrice, double exchageRate) {
        this.id = id;
        this.userId = userId;
        this.receiptProducts = receiptProducts;
        this.totalPrice = totalPrice;
        this.exchangedPrice = exchangedPrice;
        this.exchangeRate = exchageRate;
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

    public BigDecimal getExchangedPrice() {
        return exchangedPrice;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", userId=" + userId +
                ", receiptProducts=" + receiptProducts +
                ", totalPrice=" + totalPrice +
                ", exchangedPrice=" + exchangedPrice +
                '}';
    }

}
