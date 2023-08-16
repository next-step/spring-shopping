package shopping.domain.cart;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.cart.domain.CartProduct;
import shopping.product.domain.Product;

@DisplayName("장바구니 상품 테스트")
class CartProductTest {

    @Test
    @DisplayName("장바구니 상품을 정상적으로 생성한다.")
    void createCartProduct() {
        /* given */
        final Long memberId = 2L;
        final String name = "치킨";
        final String imageUrl = "image.png";
        final int price = 20000;
        final Product product = new Product(name, imageUrl, price);
        /* when */
        assertThatCode(() -> new CartProduct(memberId, product))
            .doesNotThrowAnyException();
    }
}
