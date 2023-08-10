package shopping.exchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

@DisplayName("DummyCurrencyExchangeManger 테스트")
class DummyCurrencyExchangeManagerTest {

    CurrencyExchangeManager currencyExchangeManager;

    @BeforeEach
    void setUp() {
        currencyExchangeManager = new DummyCurrencyExchangeManager();
    }

    @Test
    @DisplayName("환율 정보를 반환한다.")
    void getCurrencyRate() {
        /* given */


        /* when  */
        final double rate = currencyExchangeManager
            .findCurrencyExchangeRate(CurrencyType.KRW, CurrencyType.USD)
            .orElseThrow(() -> new IllegalStateException("환율 정보를 구할 수 없습니다."));

        /* then */
        assertThat(rate).isEqualTo(1300);
    }

    @Test
    @DisplayName("원화에서 달러로의 환율만 구할 수 있다.")
    void getCurrencyRateFailExceptKrwToUsd() {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> currencyExchangeManager
            .findCurrencyExchangeRate(CurrencyType.USD, CurrencyType.KRW))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("지원하지 않는 환율 정보입니다.");
    }
}
