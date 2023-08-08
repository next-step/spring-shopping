package shopping.application;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.Order;
import shopping.dto.request.OrderRequest;
import shopping.dto.response.OrderResponse;
import shopping.exception.OrderException;
import shopping.repository.OrderRepository;

@DisplayName("OrderService 클래스")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @DisplayName("order 메소드는")
    @Nested
    class findOrder_Method {

        @DisplayName("장바구니 상품을 주문한다")
        @Test
        void createOrder() {
            // given
            final long memberId = 1;
            final OrderRequest orderRequest = new OrderRequest();

            // when
            OrderResponse result = orderService.order(memberId, orderRequest);

            // then
            assertThat(result.getId()).isPositive();
        }

        @DisplayName("장바구니 상품이 존재하지 않으면 OrderException 을 던진다")
        @Test
        void throwOrderException_WhenOrderItemNotExist() {
            // given
            final long memberId = 1;
            final OrderRequest orderRequest = new OrderRequest();

            // when & then
            assertThatThrownBy(() -> orderService.order(memberId, orderRequest))
                .isInstanceOf(OrderException.class)
                .hasMessage("존재하지 않는 상품 정보입니다");
        }
    }
}