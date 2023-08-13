package shopping.service;

import org.springframework.stereotype.Service;
import shopping.domain.order.Order;
import shopping.dto.request.OrderExchangeRequest;
import shopping.dto.response.OrderDetailResponse;
import shopping.exception.OrderExceptionType;
import shopping.exception.ShoppingException;
import shopping.exchange.CurrencyExchanger;
import shopping.repository.OrderRepository;

@Service
public class OrderExchangeService {

    private final CurrencyExchanger currencyExchanger;
    private final OrderRepository orderRepository;

    public OrderExchangeService(
        final CurrencyExchanger currencyExchanger,
        final OrderRepository orderRepository
    ) {
        this.currencyExchanger = currencyExchanger;
        this.orderRepository = orderRepository;
    }

    public OrderDetailResponse findOrderDetailWithCurrencyExchangeRate(
        final OrderExchangeRequest request
    ) {
        final Order order = orderRepository
            .findByIdAndMemberId(request.getOrderId(), request.getMemberId())
            .orElseThrow(() ->
                new ShoppingException(OrderExceptionType.NOT_FOUND_ORDER, request.getOrderId()));

        return currencyExchanger.findCurrencyExchangeRate(request.getFrom(), request.getTo())
            .map(rate -> OrderDetailResponse.of(order, rate))
            .orElse(OrderDetailResponse.from(order));
    }
}
