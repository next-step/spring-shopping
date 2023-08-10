package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.Cart;
import shopping.domain.order.Order;
import shopping.dto.response.OrderCreateResponse;
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
    public OrderCreateResponse createOrder(final Long memberId) {
        final Cart cart = new Cart(memberId, cartProductRepository.findAllByMemberId(memberId));
        final Order order = orderRepository.save(orderMapper.mapFrom(cart));

        cartProductRepository.deleteAllByMemberId(memberId);

        return OrderCreateResponse.from(order);
    }
}
