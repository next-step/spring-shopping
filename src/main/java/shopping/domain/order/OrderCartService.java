package shopping.domain.order;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shopping.repository.CartProductRepository;
import shopping.repository.OrderRepository;

@Component
public class OrderCartService {

    private final OrderRepository orderRepository;
    private final CartProductRepository cartProductRepository;

    public OrderCartService(
        final OrderRepository orderRepository,
        final CartProductRepository cartProductRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    public Order saveOrder(final Order order) {
        final Order saved = orderRepository.save(order);
        cartProductRepository.deleteAllByMemberId(order.getMemberId());

        return saved;
    }
}
