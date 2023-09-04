package shopping.mart.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("CurrencyRate 클래스")
class CurrencyRateTest {

    @Nested
    @DisplayName("생성자는")
    class constructor_method {

        @Test
        @DisplayName("실수를 입력받아 생성한다.")
        void create_by_double() {
            // given
            Double currency = 1234.56;

            // when & then
            Assertions.assertDoesNotThrow(() -> new CurrencyRate(currency));
        }

        @Test
        @DisplayName("null을 입력받으면 value에 null을 저장한다.")
        void save_null_when_input_null() {
            // given
            Double currency = null;

            // when
            CurrencyRate currencyRate = new CurrencyRate(currency);

            // then
            Assertions.assertNull(currencyRate.getValue());
        }

        @ParameterizedTest
        @ValueSource(doubles = {0.0, -1.0})
        @DisplayName("양수가 아닌 수를 입력받으면 value에 null을 저장한다.")
        void save_null_when_input_not_positive(double currency) {
            // when
            CurrencyRate currencyRate = new CurrencyRate(currency);

            // then
            Assertions.assertNull(currencyRate.getValue());
        }
    }
}
