package shopping.dto.web.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.cart.Money;
import shopping.domain.cart.OrderItems;

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
        Long totalPrice = orderItems.totalPrice().getPrice().longValue();
        Double exchangePrice = orderItems.exchangePrice().map(Money::getPrice).orElse(null);
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
