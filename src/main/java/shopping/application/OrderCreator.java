package shopping.application;

import org.springframework.stereotype.Component;
import shopping.api.ExchangeRateAPICaller;
import shopping.dto.request.ExchangeRate;

@Component
public class OrderCreator {

    private final OrderService orderService;
    private final ExchangeRateAPICaller currencyCaller;

    public OrderCreator(OrderService orderService, ExchangeRateAPICaller currencyCaller) {
        this.orderService = orderService;
        this.currencyCaller = currencyCaller;
    }

    public Long createOrder(String email) {
        ExchangeRate exchangeRate = currencyCaller.getCurrencyRatio();
        return orderService.createOrder(email, exchangeRate);
    }
}
