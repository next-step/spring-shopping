package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class ExchangeRateTest {

    @Test
    @DisplayName("환율을 생성한다")
    void create() {
        // when & then
        assertThatNoException().isThrownBy(() -> new ExchangeRate(1300.0));
    }
}
