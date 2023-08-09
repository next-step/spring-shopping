package shopping.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.entity.OrderEntity;

public class OrderResponse {

    private final Long id;
    private final int totalPrice;
    private final Double totalPriceUSD;
    private final List<OrderItemResponse> orderItems;

    public OrderResponse(Long id, int totalPrice, Double totalPriceUSD, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.totalPriceUSD = totalPriceUSD;
        this.orderItems = orderItems;
    }

    public static OrderResponse from(OrderEntity order) {
        return new OrderResponse(
            order.getId(),
            order.getTotalPrice(),
            order.getTotalPriceUSD(),
            order.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    public Long getId() {
        return id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Double getTotalPriceUSD() {
        return totalPriceUSD;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

}
