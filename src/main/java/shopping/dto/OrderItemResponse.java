package shopping.dto;

import shopping.domain.order.OrderItem;

public class OrderItemResponse {

    private String name;
    private String image;
    private int price;
    private int quantity;

    private OrderItemResponse() {
    }

    public OrderItemResponse(String name, String image, int price, int quantity) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
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
        return new OrderItemResponse(orderItem.getProduct().getName(),
                orderItem.getProduct().getImage(),
                orderItem.getProduct().getPrice(),
                orderItem.getQuantity().getQuantity());
    }
}
