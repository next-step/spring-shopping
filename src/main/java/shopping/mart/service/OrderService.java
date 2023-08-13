package shopping.mart.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Order;
import shopping.mart.domain.Product;
import shopping.mart.persist.CartRepository;
import shopping.mart.persist.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(final OrderRepository orderRepository, final CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Long order(final long userId) {
        Cart cart = cartRepository.getCartByUserId(userId);

        List<Product> products = new ArrayList<>(cart.getProductCounts().keySet());

        Order order = new Order(products);

        return orderRepository.order(userId, order);
    }
}
