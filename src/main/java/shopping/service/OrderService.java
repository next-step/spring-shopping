package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.Cart;
import shopping.domain.order.Order;
import shopping.domain.order.OrderCartService;
import shopping.dto.response.OrderDetailResponse;
import shopping.dto.response.OrderHistoryResponse;
import shopping.exception.OrderExceptionType;
import shopping.exception.ShoppingException;
import shopping.exchange.CurrencyExchanger;
import shopping.exchange.CurrencyType;
import shopping.repository.CartProductRepository;
import shopping.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final CurrencyType DEFAULT_CURRENCY_TYPE_FROM = CurrencyType.USD;
    private static final CurrencyType DEFAULT_CURRENCY_TYPE_TO = CurrencyType.KRW;

    private final OrderMapper orderMapper;
    private final CurrencyExchanger currencyExchanger;
    private final OrderCartService orderCartService;
    private final OrderRepository orderRepository;
    private final CartProductRepository cartProductRepository;

    public OrderService(
        final OrderMapper orderMapper,
        final CurrencyExchanger currencyExchanger,
        final OrderCartService orderCartService,
        final OrderRepository orderRepository,
        final CartProductRepository cartProductRepository
    ) {
        this.orderMapper = orderMapper;
        this.currencyExchanger = currencyExchanger;
        this.orderCartService = orderCartService;
        this.orderRepository = orderRepository;
        this.cartProductRepository = cartProductRepository;
    }

    public Long createOrder(final Long memberId) {
        final Cart cart = new Cart(memberId, cartProductRepository.findAllByMemberId(memberId));

        final Order order = currencyExchanger
            .findCurrencyExchangeRate(DEFAULT_CURRENCY_TYPE_FROM, DEFAULT_CURRENCY_TYPE_TO)
            .map(rate -> orderMapper.mapOf(cart, rate.getRate()))
            .orElse(orderMapper.mapFrom(cart));

        return orderCartService.saveOrder(order).getId();
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryResponse> findOrderHistory(final Long memberId) {
        final List<Order> orders = orderRepository.findAllByMemberId(memberId);

        return orders.stream()
            .map(OrderHistoryResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse findOrderDetail(final Long memberId, final Long orderId) {
        final Order order = orderRepository.findByIdAndMemberId(orderId, memberId)
            .orElseThrow(() -> new ShoppingException(OrderExceptionType.NOT_FOUND_ORDER, orderId));

        return OrderDetailResponse.from(order);
    }
}
