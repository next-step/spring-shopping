package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.Cart;
import shopping.domain.Member;
import shopping.domain.Product;
import shopping.exception.MemberException;
import shopping.exception.ProductException;
import shopping.repository.CartRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("CartService 클래스")
@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("add 메소드는")
    class add_Method {

        @Test
        @DisplayName("member와 product를 찾아서 장바구니에 추가한다")
        void add_Product() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product = new Product(1L, "치킨", "image", 23000L);

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

            // when
            Exception exception = catchException(() -> cartService.addProduct(product.getId(), member.getId()));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("member가 존재하지 않는다면 MemberException을 던진다")
        void throwMemberException_WhenInValidMemberId() {
            // given
            Product product = new Product(1L, "치킨", "image", 23000L);

            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.addProduct(anyLong(), product.getId()));

            // then
            assertThat(exception).isInstanceOf(MemberException.class);
        }

        @Test
        @DisplayName("product가 존재하지 않는다면 ProductException을 던진다")
        void throwProductException_WhenInValidProductId() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");

            given(productRepository.findById(anyLong())).willReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.addProduct(anyLong(), member.getId()));

            // then
            assertThat(exception).isInstanceOf(ProductException.class);
        }

        @Test
        @DisplayName("memberId와 productId쌍이 이미 존재 한다면, 수량을 1개 증가한다")
        void increaseProductQuantity_WhenCartAlreadyExists() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product = new Product(1L, "치킨", "image", 23000L);
            int initialQuantity = 5;
            Cart cart = new Cart(member, product, initialQuantity);

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            given(cartRepository.findByMemberIdAndProductId(product.getId(), member.getId())).willReturn(Optional.of(cart));

            // when
            cartService.addProduct(product.getId(), member.getId());

            // then
            assertThat(cart.getQuantity()).isEqualTo(initialQuantity + 1);
        }
    }
}
