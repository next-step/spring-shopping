package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shopping.domain.EmptyExchangeRates;
import shopping.domain.ExchangeRates;

@DisplayName("CacheProvider 클래스")
class CacheProviderTest {

    private CacheProvider cacheProvider;
    private Clock clock;

    @BeforeEach
    void setUp() {
        clock = Mockito.mock(Clock.class);
        doReturn(ZoneId.systemDefault()).when(clock).getZone();
        doReturn(Instant.now()).when(clock).instant();
        cacheProvider = new CacheProvider(clock);
    }

    @DisplayName("put 메서드는")
    @Nested
    class put_Method {

        @Test
        @DisplayName("데이터를 초기화한다")
        void put() {
            // given
            ExchangeRates exchangeRates = new EmptyExchangeRates();

            // when
            cacheProvider.put(ExchangeRates.class, () -> exchangeRates);

            // then
            assertThat(cacheProvider.get(ExchangeRates.class)).isEqualTo(exchangeRates);
        }
    }

    @DisplayName("getCache 메서드는")
    @Nested
    class getCache_Method {

        @Test
        @DisplayName("데이터가 존재하지 않으면 null 을 반환한다")
        void getCache_returnNull() {
            // when & then
            assertThat(cacheProvider.get(ExchangeRates.class)).isNull();
        }

        @Test
        @DisplayName("데이터가 이미 존재하면 캐싱된 데이터를 가져온다")
        void getCache_retrieveCachedData() {
            // given
            cacheProvider.put(ExchangeRates.class, EmptyExchangeRates::new);
            final ExchangeRates cachedData = cacheProvider.get(ExchangeRates.class);

            // when & then
            assertThat(cacheProvider.get(ExchangeRates.class)).isEqualTo(cachedData);
        }

        @Test
        @DisplayName("만료시간이 지나면 캐시 데이터를 초기화한다")
        void getCache_updateExpiredCache() {
            // given
            cacheProvider.put(ExchangeRates.class, EmptyExchangeRates::new);
            final ExchangeRates initialData = cacheProvider.get(ExchangeRates.class);

            final Instant willExpireAt = Instant.now().plus(2, ChronoUnit.HOURS);
            doReturn(willExpireAt).when(clock).instant();

            // when & then
            assertThat(cacheProvider.get(ExchangeRates.class)).isNotEqualTo(initialData);
        }
    }
}