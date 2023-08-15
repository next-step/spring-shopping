package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shopping.domain.EmptyExchangeRates;
import shopping.domain.ExchangeRates;

@DisplayName("CacheProvider 클래스")
class CacheProviderTest {

    @DisplayName("getCache 메서드는")
    @Nested
    class getCache_Method {

        @Test
        @DisplayName("데이터가 null 이면 캐싱 데이터를 초기화한다")
        void getCache() {
            // given
            final Clock fixedClock = createFixedClock(Instant.now());
            final CacheProvider<ExchangeRates> cacheProvider = createMockCachedProvider(fixedClock);

            doCallRealMethod().when(cacheProvider).getData();

            // when
            final ExchangeRates data = cacheProvider.getData();

            // then
            assertThat(data).isNotNull();
            verify(cacheProvider).updateCache();
        }

        @Test
        @DisplayName("데이터가 이미 존재하면 캐싱된 데이터를 가져온다")
        void getCache_retrieveCachedData() {
            // given
            final Clock fixedClock = createFixedClock(Instant.now());
            final CacheProvider<ExchangeRates> cacheProvider = createMockCachedProvider(fixedClock);

            doCallRealMethod().when(cacheProvider).getData();

            final ExchangeRates initialData = cacheProvider.getData();

            // when & then
            assertThat(cacheProvider.getData()).isEqualTo(initialData);
            verify(cacheProvider, times(1)).updateCache();
        }
    }

    private CacheProvider<ExchangeRates> createMockCachedProvider(Clock clock) {
        return Mockito.spy(new CacheProvider<>(EmptyExchangeRates::new, clock));
    }

    private Clock createFixedClock(Instant timeInstant) {
        return Clock.fixed(timeInstant, ZoneId.systemDefault());
    }
}