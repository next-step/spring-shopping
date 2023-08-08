package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.Member;
import shopping.domain.Product;
import shopping.dto.request.OrderItemRequest;
import shopping.dto.request.OrderRequest;
import shopping.dto.response.OrderResponse;
import shopping.exception.OrderException;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;

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
    private ProductRepository productRepository;

    @DisplayName("order 메소드는")
    @Nested
    class findOrder_Method {

        @DisplayName("장바구니 상품을 주문한다")
        @Test
        void createOrder() {
            // given
            final Member member = createMember();
            final OrderRequest orderRequest = createOrderRequest();

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(new Product(1L, "a", "a", 10)));

            // when
            OrderResponse result = orderService.order(member.getId(), orderRequest);

            // then
            assertThat(result.getOrderItems())
                .hasSize(orderRequest.getOrderItems().size());
        }

        @DisplayName("장바구니 상품이 존재하지 않으면 OrderException 을 던진다")
        @Test
        void throwOrderException_WhenOrderItemNotExist() {
            // given
            final Member member = createMember();
            final OrderRequest orderRequest = createOrderRequest();

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(productRepository.findById(anyLong()))
                .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> orderService.order(member.getId(), orderRequest))
                .isInstanceOf(OrderException.class)
                .hasMessage("존재하지 않는 상품 정보입니다");
        }
    }

    private Member createMember() {
        return new Member(1L, "woowa1@woowa.com", "1234");
    }

    private OrderRequest createOrderRequest() {
        final List<OrderItemRequest> orderItemRequests = List.of(
            new OrderItemRequest(1, 1000, 10, "상품1", "url"),
            new OrderItemRequest(2, 1000, 10, "상품1", "url")
        );
        return new OrderRequest(orderItemRequests);
    }

}