package shopping.infrastructure;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.domain.cart.CurrencyType;
import shopping.exception.infrastructure.ConnectionErrorException;
import shopping.exception.infrastructure.NullResponseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@SpringBootTest
class CurrencyLayerConnectionTest {

    private final ExchangeRateFetcher mockFetcher = new MockExchangeFetcher();

    @DisplayName("정상 조회 시 환율 반환")
    @Test
    void getExchangeRate() throws NullResponseException, ConnectionErrorException {
        assertThat(mockFetcher.getExchangeRate(CurrencyType.USD, CurrencyType.KRW))
                .isCloseTo(1300.0, Offset.offset(100.0));
    }

    @DisplayName("응답 처리 불가시 예외 코드 및 메시지 전달")
    @Test
    void errorGet() {

        Exception exception = catchException(() -> mockFetcher.getExchangeRate(CurrencyType.KRW, CurrencyType.USD));
        ConnectionErrorException connectionErrorException = (ConnectionErrorException) exception;

        assertThat(connectionErrorException.getErrorCode()).isEqualTo(105);
        assertThat(connectionErrorException.getMessage()).isEqualTo("Access Restricted - Your current Subscription Plan does not support Source Currency Switching.");
    }
}

