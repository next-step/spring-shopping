package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

class ProductPriceTest {

    @Test
    @DisplayName("상품 가격을 생성한다.")
    void createProductPrice() {
        /* given */
        final int value = 1000;

        /* when & then */
        assertThatCode(() -> new ProductPrice(value))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("상품 가격이 0이하 인 경우, ShoppingException을 던진다.")
    void createProductPriceFailWithLessThanEqualZero(final int value) {
        assertThatCode(() -> new ProductPrice(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("상품 가격은 0이하면 안됩니다. 입력값: " + value);
    }
}
