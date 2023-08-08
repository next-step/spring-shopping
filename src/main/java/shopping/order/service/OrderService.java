package shopping.order.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.cart.domain.CartProductWithProduct;
import shopping.cart.repository.CartProductRepository;
import shopping.order.domain.Order;
import shopping.order.domain.OrderProduct;
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
    public Order saveOrder(final Long memberId) {
        List<CartProductWithProduct> cartProducts = cartProductRepository.findAllByMemberId(
            memberId);
        List<OrderProduct> orderProducts = cartProducts.stream().map(OrderProduct::from)
            .collect(Collectors.toList());

        Order saveOrder = orderRepository.save(new Order(orderProducts));
        cartProductRepository.deleteAllByMemberId(memberId);
        return saveOrder;
    }

}
