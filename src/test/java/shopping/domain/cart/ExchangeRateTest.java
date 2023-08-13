package shopping.domain.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.cart.NotExchangeableException;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("환율 도메인 테스트")
class ExchangeRateTest {

    @DisplayName("정상 생성")
    @Test
    void create() {
        assertThatNoException().isThrownBy(() -> new ExchangeRate(1200.0, CurrencyType.USD, CurrencyType.KRW));
    }

    @DisplayName("환전 가능")
    @Test
    void validateExchangeable() {
        // given
        ExchangeRate exchangeRate = new ExchangeRate(1200.0, CurrencyType.USD, CurrencyType.KRW);

        // when, then
        assertThatNoException().isThrownBy(() -> exchangeRate.validateExchangeable(CurrencyType.KRW));
    }

    @DisplayName("환전 불가능")
    @Test
    void notExchangeable() {
        // given
        ExchangeRate exchangeRate = new ExchangeRate(1200.0, CurrencyType.USD, CurrencyType.KRW);

        // when, then
        assertThatThrownBy(() -> exchangeRate.validateExchangeable(CurrencyType.USD))
                .isInstanceOf(NotExchangeableException.class);
    }
}
