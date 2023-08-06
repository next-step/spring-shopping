package shopping.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 1000, 20000})
    @DisplayName("가격을 생성한다.")
    void createPrice(final int value) {
        final Price price = Price.from(value);

        assertThat(price.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -21000000})
    @DisplayName("가격이 0 이하면 예외를 던진다.")
    void validatePrice(final int value) {
        Assertions.assertThatThrownBy(() -> Price.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("가격은 0원보다 커야합니다.");
    }
}
