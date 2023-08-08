package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Order;
import shopping.domain.cart.OrderItems;
import shopping.dto.response.OrderResponse;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;

import java.util.List;

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

        Order order = orderRepository.save(new Order(userId));
        OrderItems orderItems = OrderItems.from(cartItems, order);

        OrderItems savedItems = OrderItems.of(orderItemRepository.saveAll(orderItems.getItems()));
        cartItemRepository.deleteAll(cartItems);
        return OrderResponse.of(savedItems);
    }

    public OrderResponse findOrderById(Long userId, Long orderId) {
        OrderItems orderItems = OrderItems.of(orderItemRepository.findAllByOrderId(orderId));
        orderItems.validateUserOwns(userId);
        return OrderResponse.of(orderItems);
    }
}
