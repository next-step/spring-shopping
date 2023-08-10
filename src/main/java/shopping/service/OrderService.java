package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;
import shopping.domain.product.Product;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.dto.response.OrderResponses;
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
    public OrderCreateResponse createOrder(final Long memberId, final double exchangeRate) {
        final Member member = getMemberById(memberId);
        final Order persistOrder = saveOrder(member, exchangeRate);
        emptyCartItems(member);

        return OrderCreateResponse.from(persistOrder.getId());
    }

    private Order saveOrder(final Member member, final double exchangeRate) {
        final Order persistOrder = orderRepository.save(new Order(member, exchangeRate));
        final List<CartItem> cartItems = cartItemRepository.findAllByMemberId(member.getId());
        validateOrder(cartItems);
        orderItemRepository.saveAll(cartItems.stream()
                .map(convertCartItemToOrderItem(persistOrder))
                .collect(toList()));

        return persistOrder;
    }

    private void validateOrder(final List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new ShoppingException(ErrorCode.EMPTY_CART_ITEM);
        }
    }

    private void emptyCartItems(final Member member) {
        cartItemRepository.deleteAllByMember(member);
    }

    @Transactional(readOnly = true)
    public OrderResponse readOrder(final Long orderId) {
        final Order order = getOrderById(orderId);
        final List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .collect(toList());

        return OrderResponse.of(
                order.getId(),
                order.getOrderPrice(),
                order.getExchangeRate(),
                orderItemResponses
        );
    }

    @Transactional(readOnly = true)
    public OrderResponses readOrders(final Long memberId) {
        final Member member = getMemberById(memberId);
        final List<OrderResponse> response = orderRepository.findAllByMember(member).stream()
                .map(order -> readOrder(order.getId()))
                .collect(toList());

        return OrderResponses.from(response);
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

    private Order getOrderById(final Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ShoppingException(ErrorCode.NOT_FOUND_ORDER_ID));
    }
}
