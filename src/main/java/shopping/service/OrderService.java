package shopping.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.Cart;
import shopping.domain.order.Order;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderDetailResponse;
import shopping.dto.response.OrderHistoryResponse;
import shopping.exception.OrderExceptionType;
import shopping.exception.ShoppingException;
import shopping.exchange.CurrencyExchangeManager;
import shopping.exchange.CurrencyType;
import shopping.repository.CartProductRepository;
import shopping.repository.OrderRepository;

@Service
public class OrderService {

    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CurrencyExchangeManager currencyExchangeManager;

    public OrderService(
        final CartProductRepository cartProductRepository,
        final OrderRepository orderRepository,
        final OrderMapper orderMapper,
        final CurrencyExchangeManager currencyExchangeManager
    ) {
        this.cartProductRepository = cartProductRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.currencyExchangeManager = currencyExchangeManager;
    }

    @Transactional
    public OrderCreateResponse createOrder(final Long memberId) {
        final Cart cart = new Cart(memberId, cartProductRepository.findAllByMemberId(memberId));
        final Order order = orderRepository.save(orderMapper.mapFrom(cart));

        cartProductRepository.deleteAllByMemberId(memberId);

        return OrderCreateResponse.from(order);
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse findOrderDetail(final Long memberId, final Long orderId) {
        final Order order = orderRepository.findByIdAndMemberIdWithOrderProduct(orderId, memberId)
            .orElseThrow(() -> new ShoppingException(OrderExceptionType.NOT_FOUND_ORDER, orderId));

        return currencyExchangeManager.findCurrencyExchangeRate(CurrencyType.KRW, CurrencyType.USD)
            .map(rate -> OrderDetailResponse.of(order, rate))
            .orElse(OrderDetailResponse.from(order));
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryResponse> findOrderHistory(final Long memberId) {
        final List<Order> orders = orderRepository.findAllByMemberId(memberId);

        return orders.stream()
            .map(OrderHistoryResponse::from)
            .collect(Collectors.toList());
    }
}
