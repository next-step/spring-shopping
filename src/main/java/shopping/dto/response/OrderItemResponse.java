package shopping.dto.response;

import shopping.domain.order.OrderItem;

public class OrderItemResponse {
    private String image;
    private String name;
    private int price;
    private int quantity;

    private OrderItemResponse() {
    }

    private OrderItemResponse(
            final String image,
            final String name,
            final int price,
            final int quantity
    ) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getOrderItemImage(),
                orderItem.getOrderItemName(),
                orderItem.getOrderItemPrice(),
                orderItem.getOrderItemQuantity());
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
