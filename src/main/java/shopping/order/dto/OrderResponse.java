package shopping.order.dto;

import java.util.List;
import java.util.stream.Collectors;
import shopping.order.domain.Order;

public class OrderResponse {

    private Long id;
    private List<OrderProductResponse> items;
    private int totalPrice;
    private double exchangeRate;

    public OrderResponse(
        final Long id,
        final List<OrderProductResponse> items,
        final int totalPrice,
        final double exchangeRate
    ) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
        this.exchangeRate = exchangeRate;
    }

    public static OrderResponse from(final Order order) {
        List<OrderProductResponse> orderProducts = order.getOrderProducts().stream()
            .map(OrderProductResponse::from)
            .collect(Collectors.toList());
        System.out.println("asdas" + order.getExchangeRate());
        return new OrderResponse(order.getId(), orderProducts, order.getTotalPrice(), order.getExchangeRate());
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

    public double getExchangeRate() {
        return exchangeRate;
    }
}
