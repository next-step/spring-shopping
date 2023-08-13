package shopping.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.CurrencyType;
import shopping.domain.cart.ExchangeRate;
import shopping.domain.cart.Order;
import shopping.domain.cart.OrderItems;
import shopping.dto.web.response.OrderResponse;
import shopping.infrastructure.ExchangeRateFetcher;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;

@Transactional(readOnly = true)
@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ExchangeRateFetcher exchangeRateFetcher;

    public OrderService(CartItemRepository cartItemRepository, OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository, ExchangeRateFetcher exchangeRateFetcher) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.exchangeRateFetcher = exchangeRateFetcher;
    }

    @Transactional
    public OrderResponse createOrder(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);

        Order order = orderRepository.save(orderWithExchangeRate(userId));
        OrderItems orderItems = OrderItems.from(cartItems, order);

        OrderItems savedItems = OrderItems.of(orderItemRepository.saveAll(orderItems.getItems()));
        cartItemRepository.deleteAll(cartItems);
        return OrderResponse.of(savedItems);
    }

    private Order orderWithExchangeRate(Long userId) {
        Optional<Double> exchangeRate = exchangeRateFetcher.getExchangeRate(CurrencyType.USD, CurrencyType.KRW);
        return exchangeRate.map(rate -> new Order(userId, new ExchangeRate(rate, CurrencyType.USD, CurrencyType.KRW)))
                           .orElseGet(() -> new Order(userId));
    }

    public OrderResponse findOrderById(Long userId, Long orderId) {
        OrderItems orderItems = OrderItems.of(orderItemRepository.findAllByOrderId(orderId));
        orderItems.validateUserOwns(userId);
        return OrderResponse.of(orderItems);
    }

    public List<OrderResponse> findAllByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
                .map(order -> findOrderById(userId, order.getId()))
                .collect(Collectors.toList());
    }
}
