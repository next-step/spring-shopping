package shopping.order.service;

import org.springframework.stereotype.Component;
import shopping.infrastructure.ExchangeRateApi;
import shopping.infrastructure.dto.ExchangeRateResponse;
import shopping.order.dto.OrderResponse;

@Component
public class OrderCreator {

    private final OrderService orderService;
    private final ExchangeRateApi exchangeRateApi;

    public OrderCreator(OrderService orderService, ExchangeRateApi exchangeRateApi) {
        this.orderService = orderService;
        this.exchangeRateApi = exchangeRateApi;
    }

    public OrderResponse createOrder(Long memberId) {
        ExchangeRateResponse exchangeRateResponse = exchangeRateApi.callExchangeRate();
        OrderResponse orderResponse = orderService.saveOrder(memberId, exchangeRateResponse);
        return orderResponse;
    }
}
