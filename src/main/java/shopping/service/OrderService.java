package shopping.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.OrderEntity;
import shopping.domain.entity.OrderItemEntity;
import shopping.domain.entity.UserEntity;
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
    public void orderCartItem(Long userId) {
        UserEntity user = userRepository.getReferenceById(userId);
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);

        Long totalPrice = 0L;
        for (CartItemEntity cartItem: cartItems) {
            totalPrice += (cartItem.getProduct().getPrice() * cartItem.getQuantity());
        }
        OrderEntity order = new OrderEntity(totalPrice, user);

        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (CartItemEntity cartItem: cartItems) {
            orderItems.add(OrderItemEntity.from(cartItem, order));
        }
        order.addOrderItems(orderItems);

        orderRepository.save(order);
    }
}
