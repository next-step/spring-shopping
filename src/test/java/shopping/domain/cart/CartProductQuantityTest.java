package shopping.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.global.vo.Quantity;
import shopping.global.exception.ShoppingException;

@DisplayName("장바구니 상품 수량 테스트")
class CartProductQuantityTest {

    @Test
    @DisplayName("장바구니 상품 개수를 생성한다.")
    void createCartProductCount() {
        /* given */
        final int value = 7;

        /* when & then */
        assertThatCode(() -> new Quantity(value))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("장바구니 상품 개수이 0이하 인 경우, ShoppingException을 던진다.")
    void createCartProductCountFailWithLessThanEqualZero(final int value) {
        assertThatCode(() -> new Quantity(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("장바구니 상품 개수는 0이하면 안됩니다. 입력값: " + value);
    }

    @Test
    @DisplayName("장바구니 상품 개수이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final Quantity origin = new Quantity(100);
        final Quantity another = new Quantity(100);

        /* when & then */
        assertThat(origin).isEqualTo(another);
        assertThat(origin).hasSameHashCodeAs(another);
    }
}
