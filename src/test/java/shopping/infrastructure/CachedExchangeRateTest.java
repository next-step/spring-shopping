package shopping.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class CachedExchangeRateTest {

    @Test
    @DisplayName("캐싱된 환율 정보가 최신 환율인지 확인한다.")
    void isLatestCached() {
        CachedExchangeRate cachedExchangeRate = new CachedExchangeRate(1L);
        LocalDateTime pastTime = LocalDateTime.of(2023, 8, 10, 12, 1);
        cachedExchangeRate.updateExchangeRate(1300.0, pastTime);
        LocalDateTime currentTime = LocalDateTime.of(2023, 8, 10, 12, 1, 30);
        LocalDateTime futureTimeA = LocalDateTime.of(2023, 8, 10, 12, 2);
        LocalDateTime futureTimeB = LocalDateTime.of(2023, 8, 10, 12, 3);

        assertThat(cachedExchangeRate.isLatest(currentTime)).isTrue();
        assertThat(cachedExchangeRate.isLatest(futureTimeA)).isTrue();
        assertThat(cachedExchangeRate.isLatest(futureTimeB)).isFalse();
    }

    @Test
    @DisplayName("환율 정보를 캐싱한다.")
    void getExchangeRate() {
        CachedExchangeRate cachedExchangeRate = new CachedExchangeRate(1L);
        LocalDateTime pastTime = LocalDateTime.of(2023, 8, 10, 12, 1);
        cachedExchangeRate.updateExchangeRate(1300.0, pastTime);
        LocalDateTime currentTime = LocalDateTime.of(2023, 8, 10, 12, 1, 30);

        final double exchangeRate = cachedExchangeRate.getExchangeRate(currentTime);

        assertThat(exchangeRate).isCloseTo(1300.0, within(0.001));
    }

    @Test
    @DisplayName("캐싱한 환율 정보가 유효하지 않은데 접근하는 경우 예외가 발생한다.")
    void getExchangeRateFail() {
        CachedExchangeRate cachedExchangeRate = new CachedExchangeRate(1L);
        LocalDateTime pastTime = LocalDateTime.of(2023, 8, 10, 12, 1);
        cachedExchangeRate.updateExchangeRate(1300.0, pastTime);
        LocalDateTime currentTime = LocalDateTime.of(2023, 8, 10, 12, 30);

        assertThatCode(() -> cachedExchangeRate.getExchangeRate(currentTime))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("캐시된 정보가 유효하지 않습니다.");
    }
}
