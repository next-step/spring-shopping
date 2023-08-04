package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.cart.CartProduct;

@DisplayName("장바구니 상품 테스트")
class CartProductTest {

    @Test
    @DisplayName("장바구니 상품을 정상적으로 생성한다.")
    void createCartProduct() {
        /* given */
        final Long memberId = 2L;
        final Long productId = 3L;

        /* when */
        assertThatCode(() -> new CartProduct(memberId, productId))
            .doesNotThrowAnyException();
    }
}
