package shopping.order.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.domain.LoggedInMember;
import shopping.cart.dto.ProductCartItemDto;
import shopping.cart.repository.CartItemRepository;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.order.domain.Order;
import shopping.order.dto.request.OrderCreationRequest;
import shopping.order.dto.response.OrderDetailResponse;
import shopping.order.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;

    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository,
        MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.memberRepository = memberRepository;
    }

    public Long addOrder(LoggedInMember loggedInMember, OrderCreationRequest orderCreationRequest) {
        getMemberById(loggedInMember.getId());

        Order order = new Order(loggedInMember.getId());

        List<ProductCartItemDto> productCartItemDtos = cartItemRepository.findAllDtoByCartItemIds(
            orderCreationRequest.getCartItemIds()
        );

        productCartItemDtos.stream()
            .map(ProductCartItemDto::toOrderItem)
            .collect(Collectors.toList())
            .forEach(order::addOrderItem);

        orderRepository.save(order);

        cartItemRepository.deleteAll(productCartItemDtos.stream()
            .map(ProductCartItemDto::getCartItem)
            .collect(Collectors.toList())
        );

        return order.getId();
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(LoggedInMember loggedInMember, Long orderId) {
        Order order = getOrderById(orderId);
        validOrder(loggedInMember, order);
        return new OrderDetailResponse(order);
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findOrderByIdWithFetchJoinOrderItem(orderId)
            .orElseThrow(() -> new WooWaException("존재하지 않은 주문 정보입니다.", HttpStatus.BAD_REQUEST));
    }

    private void validOrder(LoggedInMember loggedInMember, Order order) {
        if (!Objects.equals(order.getMemberId(), loggedInMember.getId())) {
            throw new WooWaException("본인의 주문 정보만 조회할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new WooWaException("등록되지 않은 사람입니다.", HttpStatus.BAD_REQUEST));
    }
}
