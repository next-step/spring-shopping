package shopping.dto.response;

import shopping.domain.cart.OrderItems;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    private final Long id;
    private final List<OrderItemResponse> items;
    private final Long totalPrice;
    private final Double exchangePrice;

    private OrderResponse(Long id, List<OrderItemResponse> items, Long totalPrice, Double exchangePrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
        this.exchangePrice = exchangePrice;
    }

    public static OrderResponse of(OrderItems orderItems) {
        List<OrderItemResponse> orderItemResponses = orderItems.getItems()
                .stream()
                .map(OrderItemResponse::of)
                .collect(Collectors.toList());
        Long totalPrice = orderItems.totalPrice().getPrice();
        Double exchangePrice = orderItems.exchangePrice().orElse(null);
        return new OrderResponse(orderItems.getOrderId(), orderItemResponses, totalPrice, exchangePrice);
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

    public Double getExchangePrice() {
        return exchangePrice;
    }
}
