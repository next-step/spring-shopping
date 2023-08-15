package shopping.application;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Cart;
import shopping.domain.CartItem;
import shopping.domain.Email;
import shopping.domain.Order;
import shopping.domain.User;
import shopping.dto.request.ExchangeRate;
import shopping.dto.request.ShoppingPageRequest;
import shopping.dto.response.OrderResponse;
import shopping.exception.OrderNotFoundException;
import shopping.exception.UserNotFoundException;
import shopping.exception.UserNotMatchException;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository,
            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createOrder(String email, ExchangeRate rate) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
        List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(new Email(email));

        Order order = saveOrder(rate, user, cartItems);
        cartItemRepository.deleteAll(cartItems);
        return order.getId();
    }

    private Order saveOrder(ExchangeRate rate, User user, List<CartItem> cartItems) {
        Cart cart = new Cart(cartItems, user);
        Order order = cart.toOrderWithRatio(rate.toDomain());
        return orderRepository.save(order);
    }


    public OrderResponse findOrder(String email, Long orderId) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));
        validateAccess(user, order);

        return OrderResponse.of(order);
    }

    private static void validateAccess(User user, Order order) {
        if (order.isDifferentUser(user)) {
            throw new UserNotMatchException();
        }
    }

    public Page<OrderResponse> findAllOrder(String email, ShoppingPageRequest pageRequest) {
        Page<Order> orders = orderRepository.findAllByUserEmail(new Email(email), pageRequest);
        return orders.map(OrderResponse::of);
    }
}
