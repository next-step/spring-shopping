package shopping.dto.response;

import shopping.domain.order.OrderProduct;

public class OrderProductResponse {

    private final String orderedImage;
    private final String orderedName;
    private final int orderedPrice;
    private final int quantity;

    public OrderProductResponse(final String orderedImage, final String orderedName,
        final int orderedPrice,
        final int quantity) {
        this.orderedImage = orderedImage;
        this.orderedName = orderedName;
        this.orderedPrice = orderedPrice;
        this.quantity = quantity;
    }

    public static OrderProductResponse from(final OrderProduct orderProduct) {
        return new OrderProductResponse(
            orderProduct.getOrderedImage(),
            orderProduct.getOrderedName(),
            orderProduct.getOrderedPrice(),
            orderProduct.getQuantity()
        );
    }

    public String getOrderedImage() {
        return this.orderedImage;
    }

    public String getOrderedName() {
        return this.orderedName;
    }

    public int getOrderedPrice() {
        return this.orderedPrice;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
