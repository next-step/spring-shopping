package shopping.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartProduct;
import shopping.domain.Member;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.MemberException;
import shopping.exception.OrderException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CartProductRepository cartProductRepository;

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository,
            CartProductRepository cartProductRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.cartProductRepository = cartProductRepository;
    }

    public OrderResponse order(long memberId) {
        Member member = findMember(memberId);
        Order order = new Order(member);
        List<OrderItem> orderItems = orderMemberCartItems(member, order);

        clearMemberCart(memberId);

        return new OrderResponse(order.getId(), orderItems);
    }

    public OrderResponse getOrder(Long memberId, Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderException("존재하지 않는 주문 정보입니다"));

        validateOrderOwner(memberId, order);

        return new OrderResponse(order.getId(), order.getOrderItems());
    }

    private static void validateOrderOwner(Long memberId, Order order) {
        if (!order.isOwner(memberId)) {
            throw new OrderException("존재하지 않는 주문 정보입니다");
        }
    }

    private Member findMember(long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException("존재하지 않는 사용자 입니다"));
    }

    private List<OrderItem> orderMemberCartItems(Member member, Order order) {
        List<CartProduct> cartProducts = findMemberCartProducts(member.getId());
        List<OrderItem> orderItems = createOrderItems(order, cartProducts);
        orderRepository.save(order);
        return orderItems;
    }

    private List<CartProduct> findMemberCartProducts(long memberId){
        List<CartProduct> cartProducts = cartProductRepository.findAllByMemberId(memberId);
        if (cartProducts.isEmpty()) {
            throw new OrderException("주문할 상품이 존재하지 않습니다");
        }
        return cartProducts;
    }

    private List<OrderItem> createOrderItems(Order order, List<CartProduct> cartProducts) {
        return cartProducts.stream()
            .map(cartProduct -> new OrderItem(order, cartProduct.getProduct(),
                cartProduct.getProduct().getName(),
                cartProduct.getProduct().getPrice(), cartProduct.getQuantity(),
                cartProduct.getProduct().getImageUrl()))
            .collect(Collectors.toList());
    }

    private void clearMemberCart(long memberId) {
        cartProductRepository.deleteByMemberId(memberId);
    }
}
