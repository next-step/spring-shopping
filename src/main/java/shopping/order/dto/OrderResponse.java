package shopping.order.dto;

import java.util.List;
import java.util.stream.Collectors;
import shopping.order.domain.Order;

public class OrderResponse {

    private Long id;
    private List<OrderProductResponse> items;
    private int totalPrice;

    public OrderResponse(
        final Long id,
        final List<OrderProductResponse> items,
        final int totalPrice
    ) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public static OrderResponse from(final Order order) {
        List<OrderProductResponse> orderProducts = order.getOrderProducts().stream()
            .map(OrderProductResponse::from)
            .collect(Collectors.toList());
        return new OrderResponse(order.getId(), orderProducts, order.getTotalPrice());
    }

    public Long getId() {
        return id;
    }

    public List<OrderProductResponse> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
