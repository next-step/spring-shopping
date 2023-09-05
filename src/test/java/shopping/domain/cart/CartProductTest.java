package shopping.domain.cart;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.product.Product;

@DisplayName("장바구니 상품 테스트")
class CartProductTest {

    @Test
    @DisplayName("장바구니 상품을 정상적으로 생성한다.")
    void createCartProduct() {
        /* given */
        final Long memberId = 2L;
        final Product product = new Product("치킨", "/assets/img/chicken.png", 20000);

        /* when */
        assertThatCode(() -> new CartProduct(memberId, product))
            .doesNotThrowAnyException();
    }
}
