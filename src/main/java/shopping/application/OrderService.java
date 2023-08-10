package shopping.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartProduct;
import shopping.domain.ExchangeRate;
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
    private final ExchangeRateProvider exchangeRateProvider;

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository,
            CartProductRepository cartProductRepository, ExchangeRateProvider exchangeRateProvider) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.cartProductRepository = cartProductRepository;
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public OrderResponse order(long memberId) {
        Member member = findMember(memberId);
        ExchangeRate exchangeRate = exchangeRateProvider.getExchange("USDKRW");
        Order order = new Order(member, exchangeRate);
        List<OrderItem> orderItems = orderMemberCartItems(member, order);

        clearMemberCart(memberId);

        return mapToResponse(order, orderItems);
    }

    public OrderResponse getOrder(Long memberId, Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderException("존재하지 않는 주문 정보입니다"));

        validateOrderOwner(memberId, order);

        return mapToResponse(order, order.getOrderItems());
    }

    public List<OrderResponse> getOrders(Long memberId) {
        List<Order> orders = orderRepository.findByMemberId(memberId);
        return orders.stream()
            .map(order -> mapToResponse(order, order.getOrderItems()))
            .collect(Collectors.toList());
    }

    private OrderResponse mapToResponse(Order order, List<OrderItem> orderItems) {
        long totalPrice = 0;
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.calculateTotalPrice();
            orderItemResponses.add(OrderItemResponse.of(orderItem));
        }

        final ExchangeRate exchangeRate = order.getExchangeRate();
        final double dollarPrice = exchangeRate.exchange(totalPrice);
        return new OrderResponse(order.getId(), totalPrice, orderItemResponses, order.getExchangeRate(), dollarPrice);
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
