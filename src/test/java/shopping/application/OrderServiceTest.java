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

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(new Product(1L, "a", "a", 10)));

            // when
            OrderResponse result = orderService.order(member.getId());

            // then
            assertThat(result.getOrderItems()).isNotEmpty();
        }
    }

    private Member createMember() {
        return new Member(1L, "woowa1@woowa.com", "1234");
    }
}