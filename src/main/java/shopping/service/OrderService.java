package shopping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.OrderEntity;
import shopping.domain.entity.OrderItemEntity;
import shopping.domain.entity.UserEntity;
import shopping.dto.response.OrderIdResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(
        OrderRepository orderRepository,
        CartItemRepository cartItemRepository,
        UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderIdResponse orderCartItem(Long userId) {
        UserEntity user = userRepository.getReferenceById(userId);
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);

        int totalPrice = 0;
        for (CartItemEntity cartItem: cartItems) {
            totalPrice += (cartItem.getProduct().getPrice() * cartItem.getQuantity());
        }
        OrderEntity order = new OrderEntity(totalPrice, user);

        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (CartItemEntity cartItem: cartItems) {
            orderItems.add(OrderItemEntity.from(cartItem, order));
        }
        order.addOrderItems(orderItems);

        OrderEntity orderEntity = orderRepository.save(order);
        return new OrderIdResponse(orderEntity.getId());
    }

    public OrderResponse findOrder(Long orderId, Long userId) {
        UserEntity user = userRepository.getReferenceById(userId);
        OrderEntity order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ShoppingException(ErrorCode.INVALID_ORDER));

        return OrderResponse.from(order);
    }

    public List<OrderResponse> findOrders(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
            .map(OrderResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }
}
