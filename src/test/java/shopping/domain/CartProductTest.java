package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.cart.CartProduct;
import shopping.domain.cart.CartProductQuantity;

class CartProductTest {

    @Test
    @DisplayName("장바구니 상품을 정상적으로 생성한다.")
    void createCartProduct() {
        /* given */
        final Long id = 1L;
        final Long memberId = 2L;
        final Long productId = 3L;
        final CartProductQuantity productCount = new CartProductQuantity(4);

        /* when */
        assertThatCode(() -> new CartProduct(id, memberId, productId, productCount))
            .doesNotThrowAnyException();
    }
}
