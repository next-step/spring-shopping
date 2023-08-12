package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.application.mapper.OrderMapper;
import shopping.domain.entity.CartItem;
import shopping.domain.entity.Order;
import shopping.domain.entity.OrderItem;
import shopping.domain.vo.ExchangeRate;
import shopping.dto.OrderResponse;
import shopping.exception.OrderNotFoundException;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;

    public OrderService(final OrderRepository orderRepository,
                        final CartItemRepository cartItemRepository,
                        final OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderMapper = orderMapper;
    }

    public Long create(final Long userId, final ExchangeRate exchangeRate) {
        final List<CartItem> cartItems = findCartItemsByUserId(userId);
        final List<OrderItem> orderItems = createOrderItemsFrom(cartItems);
        cartItemRepository.deleteAllInBatch(cartItems);

        final Order order = Order.of(userId, orderItems, exchangeRate);
        return orderRepository.save(order).getId();
    }

    @Transactional(readOnly = true)
    public OrderResponse find(Long id, Long userId) {
        final Order order = findOrderByIdAndUserId(id, userId);
        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(final Long userId) {
        final List<Order> orders = findOrdersByUserId(userId);
        return convertAll(orders);
    }

    private static List<OrderResponse> convertAll(final List<Order> orders) {
        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Order> findOrdersByUserId(final Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    private Order findOrderByIdAndUserId(final Long id, final Long userId) {
        return orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(OrderNotFoundException::new);
    }

    private List<OrderItem> createOrderItemsFrom(final List<CartItem> cartItems) {
        return cartItems.stream()
                .map(orderMapper::mapItemFrom)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<CartItem> findCartItemsByUserId(final Long userId) {
        return cartItemRepository.findAllByUserId(userId);
    }
}
