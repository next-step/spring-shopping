package shopping.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThat;

class ProductPriceTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1000, 20000, 1_000_000_000})
    @DisplayName("상품 가격을 생성한다.")
    void createPrice(final int value) {
        final ProductPrice productPrice = ProductPrice.from(value);

        assertThat(productPrice.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -21000000, 1_000_000_001})
    @DisplayName("상품 가격이 0 미만 10억초과이면 예외를 던진다.")
    void validatePrice(final int value) {
        Assertions.assertThatThrownBy(() -> ProductPrice.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("가격은 0원보다 크고 10억보다 같거나 낮아야합니다.");
    }
}
