package shopping.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Order;
import shopping.domain.cart.OrderItems;
import shopping.dto.web.response.OrderResponse;
import shopping.exception.infrastructure.ConnectionErrorException;
import shopping.exception.infrastructure.NullResponseException;
import shopping.infrastructure.ExchangeRateFetcher;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class OrderService {

    // TODO: MoneyType으로 바꾸고, DB에 Price -> Money에 Money타입 저장 + Money 타입을 Double로 전환하고 Response에서 Long으로 변환
    private static final String SOURCE = "USD";
    private static final String TARGET = "KRW";

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

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
        try {
            double exchangeRate = exchangeRateFetcher.getExchangeRate(SOURCE, TARGET);
            return new Order(userId, exchangeRate);
        } catch (ConnectionErrorException e) {
            log.error("code: {}, info: {}", e.getErrorCode(), e.getMessage());
        } catch (NullResponseException e) {
            log.error(e.getMessage());
        }
        return new Order(userId);
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
