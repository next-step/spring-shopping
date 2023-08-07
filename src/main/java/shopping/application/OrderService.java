package shopping.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Cart;
import shopping.domain.CartItem;
import shopping.domain.Email;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.domain.User;
import shopping.exception.UserNotFoundException;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
            CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createOrder(String email) {
        // TODO: refactor
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
        List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(new Email(email));

        Cart cart = new Cart(cartItems);
        Order order = orderRepository.save(new Order(user, cart.getTotalPrice()));
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(order, cartItem))
                .collect(Collectors.toUnmodifiableList());
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(cartItems);
        return order.getId();
    }
}
