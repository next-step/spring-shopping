package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.domain.product.ProductPrice;
import shopping.exception.ShoppingException;

@DisplayName("상품 가격 테스트")
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

    @Test
    @DisplayName("상품 가격이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final ProductPrice origin = new ProductPrice(100);
        final ProductPrice another = new ProductPrice(100);

        /* when & then */
        assertThat(origin).isEqualTo(another);
        assertThat(origin).hasSameHashCodeAs(another);
    }
}
