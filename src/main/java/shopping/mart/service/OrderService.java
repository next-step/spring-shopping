package shopping.mart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.Cart;
import shopping.mart.domain.CurrencyRate;
import shopping.mart.domain.CurrencyType;
import shopping.mart.domain.ExchangeRateProvider;
import shopping.mart.domain.Order;
import shopping.mart.dto.OrderDetailResponse;
import shopping.mart.dto.OrderDetailResponse.OrderedProductResponse;
import shopping.mart.persist.CartRepository;
import shopping.mart.persist.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ExchangeRateProvider exchangeRateProvider;

    public OrderService(final OrderRepository orderRepository, final CartRepository cartRepository,
                        final ExchangeRateProvider exchangeRateProvider) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.exchangeRateProvider = exchangeRateProvider;
    }

    @Transactional
    public Long order(final long userId) {
        Cart cart = cartRepository.getByUserId(userId);

        CurrencyRate currencyRate = exchangeRateProvider.getCurrencyRate(CurrencyType.USD, CurrencyType.KRW);

        Order order = new Order(cart.getProductCounts(), currencyRate.getValue());

        order.setOrderId(orderRepository.order(userId, order));

        cartRepository.deleteAllProducts(userId);

        return order.getOrderId();
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse findOrderDetail(final long userId, final long orderId) {
        Order order = orderRepository.findOrderById(userId, orderId);

        return createOrderDetailResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDetailResponse> findOrderHistory(final long userId) {
        List<Order> orderHistory = orderRepository.findOrderHistory(userId);

        return orderHistory.stream()
                .map(this::createOrderDetailResponse)
                .collect(Collectors.toList());
    }

    private OrderDetailResponse createOrderDetailResponse(final Order order) {
        List<OrderedProductResponse> orderedProducts = order.getProductCounts().entrySet().stream()
                .map(item -> new OrderedProductResponse(item.getKey().getName(), item.getKey().getImageUrl(),
                        item.getKey().getPrice(), item.getValue()))
                .collect(Collectors.toList());

        return new OrderDetailResponse(order.getOrderId(), orderedProducts, order.getTotalPrice(), order.getUsd());
    }
}
