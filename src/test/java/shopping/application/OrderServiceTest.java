package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.dto.request.OrderItemRequest;
import shopping.dto.request.OrderRequest;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.OrderException;
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

    @DisplayName("order 메소드는")
    @Nested
    class findOrder_Method {

        @DisplayName("장바구니 상품을 주문한다")
        @Test
        void createOrder() {
            // given
            final long memberId = 1;
            final List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(1, 1000, 10, "상품1", "url"),
                new OrderItemRequest(2, 1000, 10, "상품1", "url")
            );
            final OrderRequest orderRequest = new OrderRequest(orderItemRequests);

            // when
            OrderResponse result = orderService.order(memberId, orderRequest);

            // then
            assertThat(result.getId()).isPositive();

            List<Long> expectedProductIds = orderItemRequests.stream()
                .map(OrderItemRequest::getProductId)
                .collect(Collectors.toList());
            List<Long> actualProductIds = result.getOrderItems()
                .stream()
                .map(OrderItemResponse::getProductId)
                .collect(Collectors.toList());
            assertThat(expectedProductIds).isEqualTo(actualProductIds);
        }

        @DisplayName("장바구니 상품이 존재하지 않으면 OrderException 을 던진다")
        @Test
        void throwOrderException_WhenOrderItemNotExist() {
            // given
            final long memberId = 1;
            final List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(1, 1000, 10, "상품1", "url"),
                new OrderItemRequest(2, 1000, 10, "상품1", "url")
            );
            final OrderRequest orderRequest = new OrderRequest(orderItemRequests);

            // when & then
            assertThatThrownBy(() -> orderService.order(memberId, orderRequest))
                .isInstanceOf(OrderException.class)
                .hasMessage("존재하지 않는 상품 정보입니다");
        }
    }
}