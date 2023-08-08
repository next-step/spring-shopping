package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

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
import shopping.domain.Product;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.MemberException;
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
            List<Long> expectedProductIds = cartProducts.stream()
                .map(cartProduct -> cartProduct.getProduct().getId())
                .collect(Collectors.toList());
            List<Long> actualProductIds = extractProductIds(result.getOrderItems());
            assertThat(actualProductIds).isEqualTo(expectedProductIds);
        }

        @DisplayName("사용자 정보가 유효하 않으면 MemberException 을 던진다.")
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
}