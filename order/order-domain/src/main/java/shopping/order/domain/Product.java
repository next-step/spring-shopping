package shopping.order.domain;

import java.math.BigInteger;

public class Product {

    private final long id;
    private final String name;
    private final BigInteger price;
    private final String imageUrl;

    public Product(long id, String name, BigInteger price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ReceiptProduct purchase(int quantity) {
        return new ReceiptProduct(id, name, calculate(quantity), imageUrl, quantity);
    }

    public BigInteger calculate(int quantity) {
        return price.multiply(BigInteger.valueOf(quantity));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigInteger getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
