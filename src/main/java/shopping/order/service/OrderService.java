package shopping.order.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.cart.domain.CartProductWithProduct;
import shopping.cart.repository.CartProductRepository;
import shopping.global.exception.ShoppingException;
import shopping.order.domain.Order;
import shopping.order.domain.OrderProduct;
import shopping.order.dto.OrderResponse;
import shopping.order.repository.OrderRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartProductRepository cartProductRepository;

    public OrderService(
        final OrderRepository orderRepository,
        final CartProductRepository cartProductRepository) {
        this.orderRepository = orderRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    public OrderResponse saveOrder(final Long memberId) {
        List<CartProductWithProduct> cartProducts = cartProductRepository.findAllByMemberId(
            memberId);
        List<OrderProduct> orderProducts = cartProducts.stream().map(OrderProduct::from)
            .collect(Collectors.toList());

        Order saveOrder = orderRepository.save(new Order(orderProducts, memberId));
        cartProductRepository.deleteAllByMemberId(memberId);
        return OrderResponse.from(saveOrder);
    }

    public OrderResponse getOrder(Long memberId, Long orderId) {
        Order order = orderRepository.findByMemberIdAndId(memberId, orderId)
            .orElseThrow(() -> new ShoppingException("찾으시는 주문이 존재하지 않습니다. 입력 값 : " + orderId));
        return OrderResponse.from(order);
    }

    public List<OrderResponse> getOrderList(Long memberId) {
        List<Order> orders = orderRepository.findByMemberId(memberId);
        return orders.stream().map(order -> OrderResponse.from(order))
            .collect(Collectors.toList());
    }
}
