package shopping.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartProduct;
import shopping.domain.Member;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.dto.response.OrderResponse;
import shopping.exception.MemberException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;

@Service
@Transactional
public class OrderService {

    OrderRepository orderRepository;
    ProductRepository productRepository;
    MemberRepository memberRepository;
    CartProductRepository cartProductRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
        MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public OrderResponse order(long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException("존재하지 않는 사용자 입니다"));

        List<CartProduct> cartProducts = cartProductRepository.findAllByMemberId(memberId);

        Order order = new Order(member);
        List<OrderItem> orderItems = cartProducts.stream()
            .map(cartProduct -> new OrderItem(order, cartProduct.getProduct(),
                cartProduct.getProduct().getName(),
                cartProduct.getProduct().getPrice(), cartProduct.getQuantity(),
                cartProduct.getProduct().getImageUrl()))
            .collect(Collectors.toList());

        orderRepository.save(order);

        return new OrderResponse(order.getId(), orderItems);
    }
}
