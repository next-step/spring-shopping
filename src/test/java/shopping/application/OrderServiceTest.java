package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.CartProduct;
import shopping.domain.Member;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.domain.Product;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.MemberException;
import shopping.exception.OrderException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;

@DisplayName("OrderService 클래스")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CartProductRepository cartProductRepository;

    @DisplayName("order 메소드는")
    @Nested
    class findOrder_Method {

        @DisplayName("장바구니 상품을 주문한다")
        @Test
        void createOrder() {
            // given
            final Member member = createMember();
            final List<CartProduct> cartProducts = createCartProducts(member);

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(cartProductRepository.findAllByMemberId(member.getId())).willReturn(cartProducts);

            // when
            OrderResponse result = orderService.order(member.getId());

            // then
            long expectedTotalPrice = cartProducts.stream()
                .mapToLong(cartProduct -> cartProduct.getQuantity() * cartProduct.getProduct().getPrice())
                .sum();
            assertThat(result.getTotalPrice()).isEqualTo(expectedTotalPrice);
        }

        @DisplayName("사용자 정보가 유효하지 않으면 MemberException 을 던진다.")
        @Test
        void throwMemberException_WhenMemberNotExist() {
            // given
            long notExistMemberId = 12L;

            given(memberRepository.findById(notExistMemberId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> orderService.order(notExistMemberId))
                .hasMessage("존재하지 않는 사용자 입니다")
                .isInstanceOf(MemberException.class);
        }

        @DisplayName("사용자 카트에 상품이 없으면 예외를 던진다.")
        @Test
        void throwOrderException_WhenCartProductIsEmpty() {
            // given
            Member member = createMember();

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(cartProductRepository.findAllByMemberId(member.getId())).willReturn(
                new ArrayList<>());

            // when & then
            assertThatThrownBy(() -> orderService.order(member.getId()))
                .hasMessage("주문할 상품이 존재하지 않습니다")
                .isInstanceOf(OrderException.class);
        }
    }

    @DisplayName("getOrder 메소드는")
    @Nested
    class getOrder_Method {

        @DisplayName("주문 상세 정보를 가져온다")
        @Test
        void getOrder() {
            // given
            Member member = createMember();
            Order order = createOrder(member);

            given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

            // when
            OrderResponse result = orderService.getOrder(member.getId(), order.getId());

            // when
            assertThat(result.getId()).isEqualTo(order.getId());
            assertThat(result.getOrderItems()).hasSize(order.getOrderItems().size());
            long expectedTotalPrice = order.getOrderItems()
                .stream()
                .mapToLong(orderItem -> orderItem.calculateTotalPrice())
                .sum();
            assertThat(result.getTotalPrice()).isEqualTo(expectedTotalPrice);
        }

        @DisplayName("주문 id 와 일치하는 주문이 없으면 OrderException 을 던진다")
        @Test
        void throwOrderException_WhenOrderNotExist() {
            // given
            long memberId = 1L;
            long notExistOrderId = 12L;

            given(orderRepository.findById(notExistOrderId)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> orderService.getOrder(memberId, notExistOrderId))
                .hasMessage("존재하지 않는 주문 정보입니다")
                .isInstanceOf(OrderException.class);
        }

        @DisplayName("해당 주문의 주문자가 아닐 경우 OrderException 을 던진다")
        @Test
        void throwOrderException_WhenMemberIsNotOwner() {
            // given
            Member member = createMember();
            Order order = createOrder(member);
            Long invalidMemberId = member.getId() + 1;

            given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

            // when & then
            assertThatThrownBy(() -> orderService.getOrder(invalidMemberId, order.getId()))
                .hasMessage("존재하지 않는 주문 정보입니다")
                .isInstanceOf(OrderException.class);
        }
    }

    private List<CartProduct> createCartProducts(Member member) {
        List<Product> products = List.of(
            new Product(1L, "상품1", "url", 10),
            new Product(2L, "상품2", "url", 20),
            new Product(3L, "상품3", "url", 30)
        );
        return products.stream()
            .map(product -> new CartProduct(product.getId(), member, product, 5))
            .collect(Collectors.toList());
    }

    private List<Long> extractProductIds(List<OrderItemResponse> responses) {
        return responses.stream()
            .map(OrderItemResponse::getProductId)
            .collect(Collectors.toList());
    }

    private Member createMember() {
        return new Member(1L, "woowa1@woowa.com", "1234");
    }

    private Order createOrder(Member member) {
        Order order = new Order(1L, member);
        Product product = new Product(1L, "chicken", "url", 10000);
        OrderItem chicken = new OrderItem(order, product, "chicken", 20000, 2, "img");
        order.addOrderItem(chicken);
        return order;
    }
}