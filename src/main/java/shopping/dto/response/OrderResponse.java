package shopping.dto.response;

import shopping.domain.cart.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    private Long id;
    private List<OrderItemResponse> orderItemResponses;
    private Long totalPrice;

    private OrderResponse(Long id, List<OrderItemResponse> orderItemResponses, Long totalPrice) {
        this.id = id;
        this.orderItemResponses = orderItemResponses;
        this.totalPrice = totalPrice;
    }

    public static OrderResponse of(Order order) {
        List<OrderItemResponse> orderItemResponses = order.getOrderItems()
                .stream()
                .map(OrderItemResponse::of)
                .collect(Collectors.toList());
        Long totalPrice = orderItemResponses.stream()
                .mapToLong(OrderItemResponse::getPrice)
                .sum();
        return new OrderResponse(order.getId(), orderItemResponses, totalPrice);
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemResponse> getOrderItemResponses() {
        return orderItemResponses;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }
}
