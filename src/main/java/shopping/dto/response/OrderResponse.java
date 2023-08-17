package shopping.dto.response;

import java.util.List;
import shopping.domain.ExchangeRate;

public class OrderResponse {

    private Long id;

    private long totalPrice;

    private List<OrderItemResponse> orderItems;

    private double exchangeRate;

    private double dollarPrice;

    private OrderResponse() {
    }

    public OrderResponse(Long id, long totalPrice, List<OrderItemResponse> orderItemResponses,
        ExchangeRate exchangeRate, double dollarPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderItems = orderItemResponses;
        this.exchangeRate = exchangeRate.getValue();
        this.dollarPrice = dollarPrice;
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

    public double getDollarPrice() {
        return dollarPrice;
    }
}
