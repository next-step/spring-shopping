package shopping.order.app.domain;

import java.math.BigInteger;

public class ReceiptProduct {

    private final long productId;
    private final String name;
    private final BigInteger price;
    private final String imageUrl;
    private final int quantity;

    public ReceiptProduct(long productId, String name, BigInteger price, String imageUrl, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
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
