package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.domain.cart.CartProductQuantity;
import shopping.exception.ShoppingException;

@DisplayName("장바구니 상품 수량 테스트")
class CartProductQuantityTest {

    @Test
    @DisplayName("장바구니 상품 개수를 생성한다.")
    void createCartProductCount() {
        /* given */
        final int value = 7;

        /* when & then */
        assertThatCode(() -> new CartProductQuantity(value))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("장바구니 상품 개수이 0이하 인 경우, ShoppingException을 던진다.")
    void createCartProductCountFailWithLessThanEqualZero(final int value) {
        assertThatCode(() -> new CartProductQuantity(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("장바구니 상품 개수는 0 이하일 수 없습니다.");
    }

    @Test
    @DisplayName("장바구니 상품 개수이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final CartProductQuantity origin = new CartProductQuantity(100);
        final CartProductQuantity another = new CartProductQuantity(100);

        /* when & then */
        assertThat(origin).isEqualTo(another);
        assertThat(origin).hasSameHashCodeAs(another);
    }
}
