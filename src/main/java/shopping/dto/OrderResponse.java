package shopping.dto;

import shopping.domain.entity.Order;
import shopping.domain.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

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

    public static OrderResponse from(final Order order) {
        final List<OrderItemResponse> itemResponses = convert(order);
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getTotalPrice().getPrice(),
                itemResponses
        );
    }

    private static List<OrderItemResponse> convert(final Order order) {
        return order.getItems()
                .stream()
                .map(OrderItemResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    private static class OrderItemResponse {

        private final long id;
        private final long productId;
        private final String name;
        private final long price;
        private final String image;
        private final int quantity;

        private OrderItemResponse(final long id,
                                  final long productId,
                                  final String name,
                                  final long price,
                                  final String image,
                                  final int quantity) {
            this.id = id;
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.image = image;
            this.quantity = quantity;
        }

        private static OrderItemResponse from(final OrderItem orderItem) {
            return new OrderItemResponse(
                    orderItem.getId(),
                    orderItem.getProductId(),
                    orderItem.getName().getName(),
                    orderItem.getPrice().getPrice(),
                    orderItem.getImage().getImage(),
                    orderItem.getQuantity().getValue()
            );
        }

        public long getId() {
            return id;
        }

        public long getProductId() {
            return productId;
        }

        public String getName() {
            return name;
        }

        public long getPrice() {
            return price;
        }

        public String getImage() {
            return image;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
