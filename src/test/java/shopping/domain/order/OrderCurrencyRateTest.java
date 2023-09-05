package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

@DisplayName("OrderCurrencyRate 단위 테스트")
class OrderCurrencyRateTest {

    @Test
    @DisplayName("정상적으로 OrderCurrencyRate를 생성한다.")
    void create() {
        /* given */
        final Double rate = 1234.5;

        /* when & then */
        assertThatCode(() -> new OrderCurrencyRate(rate))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("환율이 null일 경우 ShoppingException을 던진다.")
    void createFailWithNull() {
        /* given */


        /* when & then */
        assertThatCode(() -> new OrderCurrencyRate(null))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("환율이 존재하지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -1.23})
    @DisplayName("환율이 0보다 작거나 같을 경우 ShoppingException을 던진다.")
    void createFailWithNull(final double rate) {
        /* given */


        /* when & then */
        assertThatCode(() -> new OrderCurrencyRate(rate))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("환율은 0보다 커야합니다.");
    }
}
