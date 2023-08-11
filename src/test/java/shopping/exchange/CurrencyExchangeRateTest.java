package shopping.exchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

class CurrencyExchangeRateTest {

    @Test
    @DisplayName("환율을 소수점 둘째자리까지 반올림하여 저장한다.")
    void create() {
        /* given */
        final double rate = 1234.567;

        /* when */
        final CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate(rate);

        /* then */
        assertThat(currencyExchangeRate).isEqualTo(new CurrencyExchangeRate(1234.57));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.23, 0})
    @DisplayName("환율이 0 이하일 경우 ShoppingException을 던진다.")
    void createFailWithLessThanEqualZero(final double value) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new CurrencyExchangeRate(value))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("환율은 0 이하일 수 없습니다.");
    }
}
