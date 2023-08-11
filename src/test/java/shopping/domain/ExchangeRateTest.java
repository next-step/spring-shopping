package shopping.domain;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.Price;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class ExchangeRateTest {

    @Test
    @DisplayName("환율을 생성한다")
    void create() {
        // when & then
        assertThatNoException().isThrownBy(() -> new ExchangeRate(1300.0));
    }

    @Test
    @DisplayName("돈에 환율을 적용한다")
    void apply() {
        // given
        final ExchangeRate exchangeRate = new ExchangeRate(1300.0);
        final Price price = new Price(1000);

        // when
        final double appliedPrice = exchangeRate.apply(price);

        // then
        assertThat(appliedPrice)
                .isCloseTo(price.getValue() / exchangeRate.getValue(), Percentage.withPercentage(99));
    }
}
