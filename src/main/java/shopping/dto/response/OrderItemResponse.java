package shopping.dto.response;

import shopping.domain.order.OrderItem;

public class OrderItemResponse {

    private final String name;
    private final String image;
    private final int price;
    private final int quantity;

    private OrderItemResponse(final String name, final String image, final int price, final int quantity) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItemResponse from(final OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getName().getValue(),
                orderItem.getImage().getValue(),
                orderItem.getPrice().getValue(),
                orderItem.getQuantity().getValue()
        );
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public int getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
