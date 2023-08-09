package shopping.infrastructure;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.exception.infrastructure.ConnectionFailException;
import shopping.exception.infrastructure.NoConnectionException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CurrencyLayerConnectionTest {

    @Autowired
    private CurrencyLayerConnection connection;

    @DisplayName("정상 조회 시 환율 반환")
    @Test
    void getExchangeRate() throws NoConnectionException, ConnectionFailException {
        assertThat(connection.getExchangeRate("USD", "KRW"))
                .isCloseTo(1300.0, Offset.offset(100.0));
    }

    @DisplayName("응답 처리 불가시 예외 발생")
    @Test
    void cannotGet() {
        assertThatThrownBy(() -> connection.getExchangeRate("KRW", "USD"))
                .isInstanceOf(ConnectionFailException.class);
    }

    @DisplayName("응답 처리 불가시 예외 코드 및 메시지 전달")
    @Test
    void errorGet() {

        Exception exception = catchException(() -> connection.getExchangeRate("KRW", "USD"));
        ConnectionFailException connectionFailException = (ConnectionFailException) exception;

        assertThat(connectionFailException.getErrorCode()).isEqualTo(105);
        assertThat(connectionFailException.getMessage()).isEqualTo("Access Restricted - Your current Subscription Plan does not support Source Currency Switching.");
    }
}