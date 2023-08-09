package shopping.application;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartProduct;
import shopping.domain.Member;
import shopping.domain.Order;
import shopping.domain.OrderProduct;
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

        cartProducts.stream()
            .map(cartProduct -> OrderProduct.of(order, cartProduct))
            .forEach(order::addOrderProduct);

        cartProductRepository.deleteByMemberId(memberId);
        return orderRepository.save(order);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new OrderProductException(
                MessageFormat.format("memberId 에 해당하는 member 가 존재하지 않습니다. memberId : {0}", memberId)
            ));
    }

    private static void validateQuantity(List<CartProduct> cartProducts) {
        if (cartProducts.size() < MIN_ORDER_PRODUCT_QUANTITY) {
            throw new OrderProductException("장바구니에 상품이 존재하지 않습니다");
        }
    }
}
