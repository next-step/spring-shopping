package shopping.dto.response;

import java.util.List;
import shopping.domain.ExchangeRate;

public class OrderResponse {

    private Long id;

    private long totalPrice;

    private List<OrderItemResponse> orderItems;

    private double exchangeRate;

    private OrderResponse() {
    }

    public OrderResponse(Long id, long totalPrice, List<OrderItemResponse> orderItems, ExchangeRate exchangeRate) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
        this.exchangeRate = exchangeRate.getValue();
    }

    public Long getId() {
        return id;
    }

    public long getTotalPrice() {
        return totalPrice;
    }


    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }
}
