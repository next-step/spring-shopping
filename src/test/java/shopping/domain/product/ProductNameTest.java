package shopping.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

class ProductNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"12A456789012345BB89012345가890", "1"})
    @DisplayName("상품 이름을 생성할 수 있다.")
    void createProductName(final String value) {
        final ProductName productName = ProductName.from(value);

        assertThat(productName.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567890123456789012345678901"})
    @DisplayName("빈 문자열이나 null , 30글자 이상의 입력으로 상품 이름을 생성할 때 예외를 던진다.")
    void validateProductName(final String value) {
        assertThatThrownBy(() -> ProductName.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("상품의 이름이 올바른 형식이 아닙니다.");
    }
}
