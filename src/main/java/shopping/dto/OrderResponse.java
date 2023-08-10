package shopping.dto;

import shopping.domain.entity.Order;

import java.util.List;

public class OrderResponse {

    private final long id;
    private final long userId;
    private final long totalPrice;
    private final List<OrderItemResponse> items;

    private OrderResponse(final long id, final long userId, final long totalPrice, final List<OrderItemResponse> items) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public OrderResponse(final Order order) {
        throw new UnsupportedOperationException();
    }

    private static class OrderItemResponse {

        private final long id;
        private final long orderId;
        private final long productId;
        private final String name;
        private final long price;
        private final String image;
        private final int quantity;

        public OrderItemResponse(final long id,
                                 final long orderId,
                                 final long productId,
                                 final String name,
                                 final long price,
                                 final String image,
                                 final int quantity) {
            this.id = id;
            this.orderId = orderId;
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.image = image;
            this.quantity = quantity;
        }
    }
}
