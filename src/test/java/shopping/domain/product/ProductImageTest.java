package shopping.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

class ProductImageTest {

    @ParameterizedTest
    @ValueSource(strings = {"ABCDEFGHIJABCDEFGHIJABCDEFGHIJ", "A"})
    @DisplayName("상품의 이미지 주소를 생성한다.")
    void createProductImage(final String value) {
        final ProductImage productImage = ProductImage.from(value);

        assertThat(productImage.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("빈 문자열이나 null 이 들어오면 상품의 이미지 주소를 생성할 때 예외를 던진다.")
    void validateProductImage(final String value) {
        assertThatThrownBy(() -> ProductImage.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("상품의 이미지 주소가 올바른 형식이 아닙니다.");
    }
}
