package shopping.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.Cart;
import shopping.domain.order.Order;
import shopping.dto.response.OrderHistoryResponse;
import shopping.repository.CartProductRepository;
import shopping.repository.OrderRepository;

@Service
public class OrderService {

    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(
        final CartProductRepository cartProductRepository,
        final OrderRepository orderRepository,
        final OrderMapper orderMapper
    ) {
        this.cartProductRepository = cartProductRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public Long createOrder(final Long memberId) {
        final Cart cart = new Cart(memberId, cartProductRepository.findAllByMemberId(memberId));
        final Order order = orderRepository.save(orderMapper.mapFrom(cart));

        cleanUpCart(memberId);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryResponse> findOrderHistory(final Long memberId) {
        final List<Order> orders = orderRepository.findAllByMemberId(memberId);

        return orders.stream()
            .map(OrderHistoryResponse::from)
            .collect(Collectors.toList());
    }

    private void cleanUpCart(final Long memberId) {
        cartProductRepository.deleteAllByMemberId(memberId);
    }
}
