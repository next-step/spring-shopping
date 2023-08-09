package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
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
import shopping.domain.Product;
import shopping.exception.OrderProductException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;

@DisplayName("OrderProductService 클래스")
@ExtendWith(MockitoExtension.class)
class OrderProductServiceTest {

    @InjectMocks
    private OrderProductService orderProductService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartProductRepository cartProductRepository;

    @Nested
    @DisplayName("orderProduct 메서드는")
    class orderProduct_Method {

        @Test
        @DisplayName("장바구니에 있는 상품들을 주문한다")
        void orderProductInCart() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product1 = new Product(1L, "치킨", "image", 23000L);
            Product product2 = new Product(2L, "떡볶이", "image", 14000L);

            CartProduct cartProduct1 = new CartProduct(1L, member, product1, 3);
            CartProduct cartProduct2 = new CartProduct(1L, member, product2, 4);

            given(cartProductRepository.findAllByMemberId(member.getId())).willReturn(List.of(cartProduct1, cartProduct2));
            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

            // when
            orderProductService.orderProduct(member.getId());

            // then
            verify(cartProductRepository).deleteByMemberId(member.getId());
            verify(orderRepository).save(any(Order.class));
        }

        @Test
        @DisplayName("장바구니에 있는 상품의 개수가 0개라면 OrderProductException 을 던진다")
        void throwOrderProductException_WhenOrderProductCountIsNotPositive() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");

            given(cartProductRepository.findAllByMemberId(member.getId())).willReturn(List.of());
            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

            // when
            Exception exception = catchException(() -> orderProductService.orderProduct(member.getId()));

            // then
            assertThat(exception).isInstanceOf(OrderProductException.class);
            assertThat(exception.getMessage()).contains("장바구니에 상품이 존재하지 않습니다");
        }

    }

}