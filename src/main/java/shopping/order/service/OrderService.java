package shopping.order.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.domain.LoggedInMember;
import shopping.cart.domain.Cart;
import shopping.cart.domain.CartItem;
import shopping.cart.repository.CartItemRepository;
import shopping.order.OrderMapper;
import shopping.order.domain.Order;
import shopping.order.repository.OrderRepository;
import shopping.product.domain.Product;
import shopping.product.repository.ProductRepository;

@Service
@Transactional
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

    //혹시 상품이 변경되면?
    //혹시 나중에 상품이 변경되면? 히스토리 추적?
    public Long createOrder(LoggedInMember loggedInMember) {
        //todo: login user validation을 계속 해야하나?
        List<Product> product = productRepository.findAll();
        List<CartItem> cartItems = cartItemRepository.findAllByMemberId(loggedInMember.getId());

        Cart cart = new Cart(cartItems);
        cart.validate(product);

        Order order = orderMapper.mapToOrder(cart);
        return orderRepository.save(order).getId();
    }
}
