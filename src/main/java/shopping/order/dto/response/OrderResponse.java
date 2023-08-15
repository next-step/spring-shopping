package shopping.order.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import shopping.common.domain.Rate;
import shopping.order.domain.Order;
import shopping.common.domain.Money;

public class OrderResponse {
    private Long id;
    private List<OrderItemResponse> orderItems;
    private int totalPrice;
    private double exchangeRate;

    public OrderResponse(Long id, List<OrderItemResponse> orderItems, Money totalPrice, Rate exchangeRate) {
        this.id = id;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice.intValue();
        this.exchangeRate = exchangeRate.getValue();
    }

    private OrderResponse() {
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(),
                                 OrderItemResponse.of(order.getOrderItems()),
                                 order.getTotalPrice(),
                                 order.getExchangeRate());
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }
}
