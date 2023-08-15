package shopping.application;

import org.springframework.stereotype.Service;
import shopping.api.ExchangeRateAPICaller;
import shopping.dto.request.ExchangeRate;

@Service
public class OrderCreator {

    private final OrderService orderService;
    private final ExchangeRateAPICaller exchangeRateAPICaller;

    public OrderCreator(OrderService orderService, ExchangeRateAPICaller currencyCaller) {
        this.orderService = orderService;
        this.exchangeRateAPICaller = currencyCaller;
    }

    public Long createOrder(String email) {
        ExchangeRate exchangeRate = exchangeRateAPICaller.getExchangeRate();
        return orderService.createOrder(email, exchangeRate);
    }
}
