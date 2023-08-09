package shopping.order.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.domain.LoggedInMember;
import shopping.cart.domain.Cart;
import shopping.cart.domain.CartItem;
import shopping.cart.repository.CartItemRepository;
import shopping.exception.WooWaException;
import shopping.order.OrderMapper;
import shopping.order.domain.Order;
import shopping.order.dto.response.OrderResponse;
import shopping.order.repository.OrderRepository;
import shopping.product.domain.Product;
import shopping.product.repository.ProductRepository;

@Transactional
@Service
public class OrderService {

    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    public OrderService(CartItemRepository cartItemRepository,
                        ProductRepository productRepository,
                        OrderRepository orderRepository,
                        OrderMapper orderMapper) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public Long createOrder(LoggedInMember loggedInMember) {
        //todo: login user validation을 계속 해야하나?
        List<Product> product = productRepository.findAll();
        List<CartItem> cartItems = cartItemRepository.findAllByMemberId(loggedInMember.getId());
        Cart cart = new Cart(cartItems);
        cart.validate(product);

        Order order = orderMapper.mapToOrder(loggedInMember.getId(), cart);
        return orderRepository.save(order).getId();
    }

    public OrderResponse findOrder(LoggedInMember loggedInMember, Long orderId) {
        Order order = getOrderById(orderId);
        validateOwner(loggedInMember, order);
        return OrderResponse.from(order);
    }

    private void validateOwner(LoggedInMember loggedInMember, Order order) {
        if (!order.isOwner(loggedInMember.getId())) {
            throw new WooWaException("해당 주문의 소유자가 아닙니다. orderId: " + order.getId());
        }
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new WooWaException("존재하지 않는 주문 ID입니다 orderId: " + orderId));
    }
}
