package shopping.order.app.domain;

import java.math.BigInteger;

public class ReceiptProduct {

    private final String name;
    private final BigInteger price;
    private final String imageUrl;
    private final int quantity;

    public ReceiptProduct(String name, BigInteger price, String imageUrl, int quantity) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }
}
