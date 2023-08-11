package shopping.order.domain;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import shopping.order.domain.exception.EmptyOrderException;

public final class Order {

    private final BigInteger totalPrice;
    private final long userId;
    private final Map<Product, Integer> productsAmount;

    public Order(long userId, Map<Product, Integer> productsAmount) {
        validCart(productsAmount);
        this.userId = userId;
        this.productsAmount = productsAmount;
        this.totalPrice = calculatePrice();
    }

    private void validCart(Map<Product, Integer> productsAmount) {
        if (productsAmount == null || productsAmount.isEmpty()) {
            throw new EmptyOrderException();
        }
    }

    public BigInteger calculatePrice() {
        BigInteger calculate = BigInteger.ZERO;
        for (Entry<Product, Integer> entry : productsAmount.entrySet()) {
            calculate = calculate.add(entry.getKey().calculate(entry.getValue()));
        }
        return calculate;
    }

    public Receipt purchase(Exchange exchange) {
        List<ReceiptProduct> receiptProducts = productsAmount.entrySet().stream()
                .map(entry -> entry.getKey().purchase(entry.getValue()))
                .collect(Collectors.toList());

        return new Receipt(userId, receiptProducts, totalPrice, exchange.calculate(totalPrice), exchange.getRate());
    }

    public BigInteger getTotalPrice() {
        return totalPrice;
    }

    public long getUserId() {
        return userId;
    }
}
