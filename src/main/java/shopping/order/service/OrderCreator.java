package shopping.order.service;

import org.springframework.stereotype.Component;
import shopping.infrastructure.ExchangeRateApi;
import shopping.infrastructure.dto.ExchangeRateResponse;
import shopping.order.dto.OrderResponse;

@Component
public class OrderCreator {

    private final OrderService orderService;
    private final ExchangeRateApi exchangeRateApi;

    public OrderCreator(final OrderService orderService, final ExchangeRateApi exchangeRateApi) {
        this.orderService = orderService;
        this.exchangeRateApi = exchangeRateApi;
    }

    public OrderResponse createOrder(final Long memberId) {
        ExchangeRateResponse exchangeRateResponse = exchangeRateApi.callExchangeRate();
        return orderService.saveOrder(memberId, exchangeRateResponse.getRate());
    }
}
