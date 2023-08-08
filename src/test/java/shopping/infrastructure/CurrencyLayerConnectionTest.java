package shopping.infrastructure;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CurrencyLayerConnectionTest {

    @Autowired
    private CurrencyLayerConnection connection;

    @DisplayName("정상 조회 시 환율 반환")
    @Test
    void getExchangeRate() {
        assertThat(connection.getExchangeRate("USD", "KRW"))
                .isCloseTo(1300, Offset.offset(100L));
    }

    @DisplayName("응답 처리 불가시 예외 발생")
    @Test
    void cannotGet() {
        assertThatThrownBy(() -> connection.getExchangeRate("KRW", "USD"))
                .isInstanceOf(RuntimeException.class);
    }
}