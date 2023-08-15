package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shopping.domain.EmptyExchangeRates;
import shopping.domain.ExchangeRates;

@DisplayName("CacheProvider 클래스")
class CacheProviderTest {

    CacheProvider<ExchangeRates> cacheProvider;

    @BeforeEach
    void setUp() {
        cacheProvider = Mockito.spy(new CacheProvider<>(EmptyExchangeRates::new));
    }

    @DisplayName("getCache 메서드는")
    @Nested
    class getCache_Method {

        @Test
        @DisplayName("데이터가 null 이면 캐싱 데이터를 초기화한다")
        void getCache() {
            // given
            doCallRealMethod().when(cacheProvider).getData();

            // when
            final ExchangeRates data = cacheProvider.getData();

            // then
            verify(cacheProvider).updateCache();

            assertThat(data).isNotNull();
        }

        @Test
        @DisplayName("데이터가 이미 존재하면 캐싱된 데이터를 가져온다")
        void getCache_retrieveCachedData() {
            // given
            doCallRealMethod().when(cacheProvider).getData();
            final ExchangeRates initialData = cacheProvider.getData();

            // when
            final ExchangeRates cachedData = cacheProvider.getData();

            // then
            verify(cacheProvider, times(1)).updateCache();

            assertThat(cachedData).isEqualTo(initialData);

        }
    }
}