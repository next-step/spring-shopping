package shopping.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.cart.domain.CartProduct;
import shopping.cart.dto.request.CartProductCreateRequest;
import shopping.cart.repository.CartProductRepository;
import shopping.cart.service.CartService;
import shopping.global.exception.ShoppingException;
import shopping.product.domain.Product;
import shopping.product.repository.ProductRepository;

@DisplayName("장바구니 상품 Service 테스트")
@ExtendWith(MockitoExtension.class)
class CartProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CartProductRepository cartProductRepository;

    @InjectMocks
    CartService cartService;

    @Test
    @DisplayName("장바구니 상품을 추가할 수 있다")
    void createCartProduct() {
        /* given */
        final Long memberId = 1L;
        final Long productId = 1L;
        Product product = new Product("치킨, ", "/asset/img/chicken", 20000);

        given(productRepository.findById(productId)).willReturn(
            Optional.of(product)
        );
        given(cartProductRepository.findByMemberIdAndProduct(memberId, product))
            .willReturn(Optional.empty());

        /* when */
        cartService.createCartProduct(memberId, new CartProductCreateRequest(productId));

        /* then */
        verify(productRepository, atLeast(1)).findById(productId);
        verify(cartProductRepository, atLeast(1))
            .findByMemberIdAndProduct(memberId, product);
    }

    @Test
    @DisplayName("상품이 존재하지 않는 경우 ShoppingException을 던진다.")
    void createCartProductFailWithDoesNotExistProduct() {
        /* given */
        final Long memberId = 1L;
        final Long productId = 123L;

        given(productRepository.findById(productId)).willReturn(Optional.empty());

        /* when & then */
        assertThatCode(
            () -> cartService.createCartProduct(memberId,
                new CartProductCreateRequest(productId))
        )
            .isInstanceOf(ShoppingException.class)
            .hasMessage("존재하지 않는 상품입니다. 입력값: " + productId);

        verify(productRepository, times(1)).findById(productId);
        verify(cartProductRepository, times(0))
            .findByMemberIdAndProduct(eq(memberId), any(Product.class));
    }

    @Test
    @DisplayName("장바구니 상품이 이미 존재하는 경우 ShoppingException을 던진다.")
    void createCartProductFailWithExistCartProduct() {
        /* given */
        final Long memberId = 1L;
        final Long productId = 1L;

        Product product = new Product(productId, "치킨", "/asset/img/chicken", 20000);
        given(productRepository.findById(productId)).willReturn(
            Optional.of(product)
        );
        given(cartProductRepository.findByMemberIdAndProduct(memberId, product)).willReturn(
            Optional.of(new CartProduct(memberId, product))
        );

        /* when & then */
        assertThatCode(
            () -> cartService.createCartProduct(memberId,
                new CartProductCreateRequest(productId))
        ).isInstanceOf(ShoppingException.class)
            .hasMessage("이미 장바구니에 담긴 상품입니다. 입력값: " + productId);

        verify(productRepository, times(1)).findById(productId);
        verify(cartProductRepository, times(1))
            .findByMemberIdAndProduct(memberId, product);
    }
}
