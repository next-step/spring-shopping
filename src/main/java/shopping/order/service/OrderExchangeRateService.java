package shopping.order.service;

import org.springframework.stereotype.Service;
import shopping.auth.domain.LoggedInMember;
import shopping.order.dto.response.OrderResponse;

@Service

public class OrderExchangeRateService {

    public final OrderService orderService;
    public final ExchangeRateService exchangeRateService;

    public OrderExchangeRateService(OrderService orderService, ExchangeRateService exchangeRateService) {
        this.orderService = orderService;
        this.exchangeRateService = exchangeRateService;
    }

    public OrderResponse createOrder(LoggedInMember loggedInMember) {
        return orderService.createOrder(loggedInMember, exchangeRateService.getExchangeRate(
            ExchangeType.USD, ExchangeType.KRW));
    }
}
