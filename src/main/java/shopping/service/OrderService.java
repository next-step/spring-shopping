package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;
import shopping.domain.product.Product;
import shopping.dto.response.OrderCreateResponse;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;

    public OrderService(
            final OrderRepository orderRepository,
            final CartItemRepository cartItemRepository,
            final OrderItemRepository orderItemRepository,
            final MemberRepository memberRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public OrderCreateResponse createOrder(final Long memberId) {
        final Member member = getMemberById(memberId);
        final Order persistOrder = orderRepository.save(new Order(member));
        final List<CartItem> cartItems = cartItemRepository.findAllByMemberId(memberId);

        orderItemRepository.saveAll(cartItems.stream()
                .map(convertCartItemToOrderItem(persistOrder))
                .collect(toList()));
        cartItemRepository.deleteAllByMember(member);

        return OrderCreateResponse.from(persistOrder.getId());
    }

    private Function<CartItem, OrderItem> convertCartItemToOrderItem(final Order order) {
        return cartItem -> {
            final Product product = cartItem.getProduct();
            return new OrderItem(
                    product.getName(),
                    product.getPrice(),
                    product.getImage(),
                    cartItem.getQuantity(),
                    order);
        };
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ShoppingException(ErrorCode.NOT_FOUND_MEMBER_ID));
    }


}
