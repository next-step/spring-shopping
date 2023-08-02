package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import shopping.domain.product.ProductName;
import shopping.exception.ShoppingException;

class ProductNameTest {

    @Test
    @DisplayName("상품 이름을 생성한다.")
    void createProductName() {
        /* given */
        final String value = "마라탕";

        /* when & then */
        assertThatCode(() -> new ProductName(value))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품 이름이 null이거나 공백일 때 ShoppingException을 던진다.")
    void createProductNameFailWithNullOrEmptyValue(final String value) {
        /* given */


        /* when & then */
        assertThatCode(() -> new ProductName(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("상품 이름은 비어있거나 공백이면 안됩니다. 입력값: " + value);
    }

    @Test
    @DisplayName("상품 이름이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final ProductName origin = new ProductName("마라탕");
        final ProductName another = new ProductName("마라탕");

        /* when & then */
        assertThat(origin).isEqualTo(another);
        assertThat(origin).hasSameHashCodeAs(another);
    }
}
