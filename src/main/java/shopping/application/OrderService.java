package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItemRepository;
import shopping.domain.cart.CartItems;
import shopping.domain.order.Order;
import shopping.domain.order.OrderRepository;
import shopping.dto.OrderDetailResponse;
import shopping.dto.OrderResponse;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(CartItemRepository cartItemRepository, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public Long createFromCart(Long userId) {
        CartItems items = findCartItemsByUserId(userId);
        Order order = orderMapper.mapOrderFrom(userId, items);

        Long id = orderRepository.save(order).getId();
        cartItemRepository.deleteAll(items.getItems());

        return id;
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse findById(Long userId, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ShoppingException(ErrorType.ORDER_NO_EXIST, id));

        if (!Objects.equals(order.getUserId(), userId)) {
            throw new ShoppingException(ErrorType.ORDER_UNAUTHORIZED);
        }

        return OrderDetailResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    private CartItems findCartItemsByUserId(final Long userId) {
        return new CartItems(cartItemRepository.findAllByUserId(userId));
    }
}
