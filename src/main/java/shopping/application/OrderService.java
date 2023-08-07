package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Order;
import shopping.domain.cart.OrderItem;
import shopping.domain.cart.OrderItems;
import shopping.dto.response.OrderResponse;
import shopping.exception.NoCartItemForOrderException;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(CartItemRepository cartItemRepository, OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderResponse createOrder(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        validateCartItemExists(cartItems);

        Order order = orderRepository.save(new Order(userId));
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.from(cartItem, order))
                .collect(Collectors.toList());
        return OrderResponse.of(OrderItems.of(orderItemRepository.saveAll(orderItems)));

    }

    private void validateCartItemExists(List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new NoCartItemForOrderException();
        }
    }

    public OrderResponse findOrderById(Long orderId) {
        return OrderResponse.of(OrderItems.of(orderItemRepository.findAllByOrderId(orderId)));
    }
}
