package shopping.order.app.domain;

import java.math.BigInteger;

public class Product {

    private final long productId;
    private final String name;
    private final BigInteger price;
    private final String imageUrl;

    public Product(long productId, String name, BigInteger price, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ReceiptProduct purchase(int quantity) {
        return new ReceiptProduct(productId, name, calculate(quantity), imageUrl, quantity);
    }

    public BigInteger calculate(int quantity) {
        return price.multiply(BigInteger.valueOf(quantity));
    }
}
