package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItemRepository;
import shopping.domain.cart.CartItems;
import shopping.domain.cart.Quantity;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;
import shopping.domain.order.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public OrderService(CartItemRepository cartItemRepository, OrderRepository orderRepository) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Long create(Long userId) {
        CartItems items = findCartItemsByUserId(userId);

        List<OrderItem> orderItems = items.getItems()
                .stream()
                .map(item -> new OrderItem(item.getProduct(), new Quantity(item.getQuantity())))
                .collect(Collectors.toList());

        long sum = items.getItems()
                .stream()
                .mapToInt(item -> item.getProduct().getPrice())
                .sum();

        Order order = new Order(userId, orderItems, sum);

        return orderRepository.save(order).getId();
    }

    private CartItems findCartItemsByUserId(final Long userId) {
        return new CartItems(cartItemRepository.findAllByUserId(userId));
    }
}
