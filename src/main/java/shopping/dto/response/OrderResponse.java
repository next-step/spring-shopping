package shopping.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.Order;
import shopping.domain.OrderItem;

public class OrderResponse {

    private final Long id;

    private final Long totalPrice;

    private final List<OrderItemResponse> orderItems;

    private OrderResponse(Long id, Long totalPrice, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public static OrderResponse of(Order order, List<OrderItem> orderItems) {
        return new OrderResponse(order.getId(),
                order.getTotalPrice().getPrice(),
                orderItems.stream()
                        .map(OrderItemResponse::of)
                        .collect(Collectors.toUnmodifiableList()));
    }

    public static OrderResponse of(Order order) {
        return new OrderResponse(order.getId(),
                order.getTotalPrice().getPrice(),
                order.getOrderItems().stream()
                        .map(OrderItemResponse::of)
                        .collect(Collectors.toUnmodifiableList()));
    }

    public Long getId() {
        return id;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
