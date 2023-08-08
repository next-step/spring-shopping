package shopping.order.service;

import java.util.List;
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

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new WooWaException("등록되지 않은 사람입니다.", HttpStatus.BAD_REQUEST));
    }
}
