package shopping.dto.response;

import shopping.domain.cart.OrderItems;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    private Long id;
    private List<OrderItemResponse> items;
    private Long totalPrice;

    private OrderResponse(Long id, List<OrderItemResponse> items, Long totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public static OrderResponse of(OrderItems orderItems) {
        List<OrderItemResponse> orderItemResponses = orderItems.getItems()
                .stream()
                .map(OrderItemResponse::of)
                .collect(Collectors.toList());
        Long totalPrice = orderItemResponses.stream()
                .mapToLong(OrderItemResponse::getPrice)
                .sum();
        return new OrderResponse(orderItems.getOrderId(), orderItemResponses, totalPrice);
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }
}
