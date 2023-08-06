package shopping.application;

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
import shopping.dto.CartProductResponse;
import shopping.dto.CartProductRequest;
import shopping.exception.MemberException;
import shopping.exception.ProductException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@DisplayName("CartService 클래스")
@ExtendWith(MockitoExtension.class)
public class CartProductServiceTest {

    @InjectMocks
    private CartProductService cartProductService;

    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("add 메소드는")
    class add_Method {

        @Test
        @DisplayName("member 와 product 를 찾아서 장바구니에 추가한다")
        void add_Product() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product = new Product(1L, "치킨", "image", 23000L);

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

            // when
            Exception exception = catchException(() -> cartProductService.addProduct(product.getId(), member.getId()));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("member 가 존재하지 않는다면 MemberException 을 던진다")
        void throwMemberException_WhenInValidMemberId() {
            // given
            Product product = new Product(1L, "치킨", "image", 23000L);

            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartProductService.addProduct(anyLong(), product.getId()));

            // then
            assertThat(exception).isInstanceOf(MemberException.class);
            assertThat(exception.getMessage()).contains("회원Id에 해당하는 회원이 존재하지 않습니다");
        }

        @Test
        @DisplayName("product 가 존재하지 않는다면 ProductException 을 던진다")
        void throwProductException_WhenInValidProductId() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");

            given(productRepository.findById(anyLong())).willReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartProductService.addProduct(anyLong(), member.getId()));

            // then
            assertThat(exception).isInstanceOf(ProductException.class);
            assertThat(exception.getMessage()).contains("상품Id에 해당하는 상품 존재하지 않습니다");
        }

        @Test
        @DisplayName("memberId와 productId 쌍이 이미 존재 한다면, 수량을 1개 증가한다")
        void increaseProductQuantity_WhenCartAlreadyExists() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product = new Product(1L, "치킨", "image", 23000L);
            int initialQuantity = 5;
            CartProduct cart = new CartProduct(member, product, initialQuantity);

            given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            given(cartProductRepository.findOneByMemberIdAndProductId(product.getId(), member.getId())).willReturn(Optional.of(cart));

            // when
            cartProductService.addProduct(product.getId(), member.getId());

            // then
            assertThat(cart.getQuantity()).isEqualTo(initialQuantity + 1);
        }
    }

    @Nested
    @DisplayName("findCartProducts 메서드는")
    class FindCartProducts_Method {

        @Test
        @DisplayName("장바구니에 있는 상품들을 반환한다.")
        void returnCartProducts() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product = new Product(1L, "치킨", "image", 23000L);
            int initialQuantity = 5;
            CartProduct cartProduct = new CartProduct(1L, member, product, initialQuantity);

            given(cartProductRepository.findAllByMemberId(member.getId())).willReturn(List.of(cartProduct));

            // when
            List<CartProductResponse> result = cartProductService.findCartProducts(member.getId());

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getProductId()).isEqualTo(product.getId());
        }
    }

    @Nested
    @DisplayName("deleteCartProduct 메서드는")
    class DeleteCartProduct_Method {

        @Test
        @DisplayName("장바구니에 있는 상품을 제거한다")
        void deleteCartProduct() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product = new Product(1L, "치킨", "image", 23000L);
            int initialQuantity = 5;
            CartProduct cartProduct = new CartProduct(member, product, initialQuantity);

            doNothing().when(cartProductRepository).deleteById(cartProduct.getId());

            // when
            cartProductService.deleteCartProduct(cartProduct.getId());

            // then
            verify(cartProductRepository).deleteById(cartProduct.getId());
        }
    }

    @Nested
    @DisplayName("updateCartProduct 메서드는")
    class UpdateCartProduct_Method {

        @Test
        @DisplayName("장바구니에 있는 상품 수량을 변경한다")
        void updateCartProduct() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product = new Product(1L, "치킨", "image", 23000L);
            CartProduct cartProduct = new CartProduct(member, product, 5);
            CartProductRequest updateRequest = new CartProductRequest(6);

            doNothing().when(cartProductRepository).updateById(cartProduct.getId(), updateRequest.getQuantity());

            // when
            cartProductService.updateCartProduct(cartProduct.getId(), updateRequest);

            // then
            verify(cartProductRepository).updateById(cartProduct.getId(), updateRequest.getQuantity());
        }

        @Test
        @DisplayName("갱신될 상품 수량이 0개일 경우 장바구니의 상품을 제거한다")
        void deleteCartProduct_WhenQuantityIsZero() {
            // given
            Member member = new Member(1L, "home@woowa.com", "1234");
            Product product = new Product(1L, "치킨", "image", 23000L);
            CartProduct cartProduct = new CartProduct(1L, member, product, 5);
            CartProductRequest updateRequest = new CartProductRequest(0);

            doNothing().when(cartProductRepository).deleteById(cartProduct.getId());

            // when
            cartProductService.updateCartProduct(product.getId(), updateRequest);

            // then
            verify(cartProductRepository).deleteById(cartProduct.getId());
        }
    }
}
