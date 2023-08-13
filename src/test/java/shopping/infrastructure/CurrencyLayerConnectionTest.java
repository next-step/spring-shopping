package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.domain.cart.CurrencyType;

@SpringBootTest
class CurrencyLayerConnectionTest {

    private final ExchangeRateFetcher mockFetcher = new MockExchangeFetcher();

    @DisplayName("정상 조회 시 환율 반환")
    @Test
    void getExchangeRate() {
        assertThat(mockFetcher.getExchangeRate(CurrencyType.USD, CurrencyType.KRW))
                .isPresent().get().isEqualTo(1300.0);
    }

    @DisplayName("응답 처리 불가시 null 반환")
    @Test
    void errorGet() {
        Optional<Double> exchangeRate = mockFetcher.getExchangeRate(CurrencyType.KRW, CurrencyType.USD);

        assertThat(exchangeRate).isEmpty();
    }
}

