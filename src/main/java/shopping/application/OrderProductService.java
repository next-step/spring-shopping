package shopping.application;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartProduct;
import shopping.domain.Member;
import shopping.domain.Order;
import shopping.domain.OrderProduct;
import shopping.dto.OrderDetailResponse;
import shopping.exception.OrderProductException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;

@Service
@Transactional
public class OrderProductService {

    public static final int MIN_ORDER_PRODUCT_QUANTITY = 1;

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final CartProductRepository cartProductRepository;

    public OrderProductService(
        MemberRepository memberRepository, OrderRepository orderRepository, CartProductRepository cartProductRepository
    ) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.cartProductRepository = cartProductRepository;
    }

    public long orderProduct(Long memberId) {
        Member member = getMember(memberId);

        List<CartProduct> cartProducts = cartProductRepository.findAllByMemberId(member.getId());
        validateQuantity(cartProducts);

        Order order = new Order(member);
        addOrderProducts(cartProducts, order);

        cartProductRepository.deleteByMemberId(memberId);

        return orderRepository.save(order);
    }

    private void addOrderProducts(List<CartProduct> cartProducts, Order order) {
        cartProducts.stream()
            .map(cartProduct -> OrderProduct.of(order, cartProduct))
            .forEach(order::addOrderProduct);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new OrderProductException(
                MessageFormat.format("memberId 에 해당하는 member 가 존재하지 않습니다. memberId : {0}", memberId)
            ));
    }

    private void validateQuantity(List<CartProduct> cartProducts) {
        if (cartProducts.size() < MIN_ORDER_PRODUCT_QUANTITY) {
            throw new OrderProductException("장바구니에 상품이 존재하지 않습니다");
        }
    }

    public OrderDetailResponse findOrder(long orderId) {
        Order order = getOrder(orderId);

        return OrderDetailResponse.from(order);
    }

    private Order getOrder(long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderProductException(
                MessageFormat.format("orderId 에 해당하는 order 가 존재하지 않습니다 orderId : {0}", orderId)
            ));
    }

}
