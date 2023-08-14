package shopping.order.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.cart.domain.CartProduct;
import shopping.cart.repository.CartProductRepository;
import shopping.global.exception.ShoppingException;
import shopping.infrastructure.dto.ExchangeRateResponse;
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
        final CartProductRepository cartProductRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    public OrderResponse saveOrder(final Long memberId,
        ExchangeRateResponse exchangeRateResponse) {
        List<CartProduct> cartProducts = cartProductRepository
            .findAllByMemberId(memberId);

        if (cartProducts.isEmpty()) {
            throw new ShoppingException("주문하실 상품이 존재하지 않습니다.");
        }

        Order order = new Order(memberId, exchangeRateResponse);
        orderRepository.save(order);

        List<OrderProduct> orderProducts = convertOrderProduct(cartProducts);
        order.addOrderProducts(orderProducts);

        cartProductRepository.deleteAllByMemberId(memberId);
        return OrderResponse.from(order);
    }

    private List<OrderProduct> convertOrderProduct(
        final List<CartProduct> cartProducts) {
        return cartProducts.stream()
            .map(OrderProduct::from)
            .collect(Collectors.toList());
    }

    public OrderResponse getOrder(final Long memberId, final Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(
                () -> new ShoppingException("찾으시는 주문이 존재하지 않습니다. 입력 값 : " + orderId)
            );

        if (!order.matchPersonOrder(memberId)) {
            throw new ShoppingException("주문자가 현재 로그인한 사람이 아닙니다. 입력 값 : " + memberId);
        }

        return OrderResponse.from(order);
    }

    public List<OrderResponse> getOrderList(final Long memberId) {
        List<Order> orders = orderRepository.findByMemberId(memberId);
        return orders.stream()
            .map(OrderResponse::from)
            .collect(Collectors.toList());
    }
}
