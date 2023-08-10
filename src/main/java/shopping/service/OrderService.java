package shopping.service;

import static java.util.stream.Collectors.toList;
import static shopping.exception.ShoppingErrorType.INVALID_ORDER_REQUEST;
import static shopping.exception.ShoppingErrorType.NOT_FOUND_MEMBER_ID;
import static shopping.exception.ShoppingErrorType.NOT_FOUND_ORDER_ID;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;
import shopping.dto.response.OrderHistoryResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;

@Service
public class OrderService {

    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public OrderService(
            final MemberRepository memberRepository,
            final CartItemRepository cartItemRepository,
            final OrderRepository orderRepository
    ) {
        this.memberRepository = memberRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse createOrder(final Long memberId, final double exchangeRate) {
        final Member member = getMemberById(memberId);
        final List<CartItem> cartItems = cartItemRepository.findAllByMemberId(memberId);
        validateEmptyCartItems(cartItems);

        final List<OrderItem> orderItems = cartItems.stream()
                .map(OrderItem::new)
                .collect(toList());
        final Order order = orderRepository.save(new Order(member, orderItems, exchangeRate));
        cartItemRepository.deleteAllInBatch(cartItems);

        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse readOrderDetail(final Long orderId) {
        final Order order = getOrderById(orderId);

        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryResponse> readOrderHistories(final Long memberId) {
        return orderRepository.findAllByMemberId(memberId).stream()
                .map(OrderHistoryResponse::from)
                .collect(toList());
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new ShoppingException(NOT_FOUND_MEMBER_ID));
    }

    private Order getOrderById(final Long orderId) {
        return orderRepository.findOrderWithOrderItemsById(orderId)
                .orElseThrow(() -> new ShoppingException(NOT_FOUND_ORDER_ID));
    }

    private void validateEmptyCartItems(final List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new ShoppingException(INVALID_ORDER_REQUEST);
        }
    }
}
