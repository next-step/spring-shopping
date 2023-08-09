package shopping.order.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.order.domain.Order;
import shopping.product.domain.vo.Money;

public class OrderResponse {
    private Long id;
    private List<OrderItemResponse> orderItems;
    private Money totalPrice;

    public OrderResponse(Long id, List<OrderItemResponse> orderItems, Money totalPrice) {
        this.id = id;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    private OrderResponse() {
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(),
                                 OrderItemResponse.of(order.getOrderItems()),
                                 order.getTotalPrice());
    }

    public static List<OrderResponse> of(List<Order> orders) {
        return orders.stream()
            .map(OrderResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }
}
