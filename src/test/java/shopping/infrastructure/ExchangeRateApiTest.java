package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ExchangeRateApiTest {
    @Test
    @DisplayName("exchangeRateApi 를 통해 환율을 얻을 수 있다.")
    void api_이용하여_환율_얻음(){
        // given
        Double actual = new MockExchangeRateApi().callExchangeRate().getRate();
        // when & then
        assertThat(actual).isCloseTo(1300.1, offset(0.01));
        assertThat(actual).isNotCloseTo(1300.2,offset(0.01));
    }
}
