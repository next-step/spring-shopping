package shopping.dto;

import shopping.domain.order.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailResponse {

    private Long id;
    private List<OrderItemResponse> items;
    private long totalPrice;
    private double exchangeRate;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(final Long id,
                               final List<OrderItemResponse> orderItems,
                               final long totalPrice,
                               final double exchangeRate) {
        this.id = id;
        this.items = orderItems;
        this.totalPrice = totalPrice;
        this.exchangeRate = exchangeRate;
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public static OrderDetailResponse from(Order order) {
        return new OrderDetailResponse(
                order.getId(),
                order.getOrderItems()
                        .stream()
                        .map(OrderItemResponse::from)
                        .collect(Collectors.toList()),
                order.getTotalPrice(),
                order.getExchangeRate()
        );
    }
}
