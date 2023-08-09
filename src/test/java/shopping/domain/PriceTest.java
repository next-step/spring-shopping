package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.cart.domain.vo.Price;
import shopping.global.exception.ShoppingException;

@DisplayName("상품 가격 테스트")
class PriceTest {

    @Test
    @DisplayName("상품 가격을 생성한다.")
    void createProductPrice() {
        /* given */
        final int value = 1000;

        /* when & then */
        assertThatCode(() -> new Price(value))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("상품 가격이 0이하 인 경우, ShoppingException을 던진다.")
    void createProductPriceFailWithLessThanEqualZero(final int value) {
        // when & then
        assertThatCode(() -> new Price(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("상품 가격은 0이하면 안됩니다. 입력값: " + value);
    }

    @Test
    @DisplayName("상품 가격이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final Price origin = new Price(100);
        final Price another = new Price(100);

        /* when & then */
        assertThat(origin).isEqualTo(another);
        assertThat(origin).hasSameHashCodeAs(another);
    }
}
