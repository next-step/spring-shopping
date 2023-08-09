package shopping.dto;

import shopping.domain.OrderProduct;

public class OrderProductResponse {

    private final String name;
    private final String imageUrl;
    private final long price;
    private final int quantity;

    private OrderProductResponse(String name, String imageUrl, long price, int quantity) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(
            orderProduct.getProduct().getName(),
            orderProduct.getProduct().getImageUrl(),
            orderProduct.getProduct().getPrice(),
            orderProduct.getQuantity()
        );
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
