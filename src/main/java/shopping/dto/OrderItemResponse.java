package shopping.dto;

import shopping.domain.order.OrderItem;

public class OrderItemResponse {

    private long productId;
    private String name;
    private String image;
    private int price;
    private int quantity;

    private OrderItemResponse() {
    }

    public OrderItemResponse(Long productId, String name, String image, int price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.getProductId(),
                orderItem.getName().getName(),
                orderItem.getImage().getImage(),
                orderItem.getPrice().getPrice(),
                orderItem.getQuantity().getQuantity());
    }
}
