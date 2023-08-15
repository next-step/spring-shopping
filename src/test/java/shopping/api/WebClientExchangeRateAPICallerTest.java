package shopping.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import shopping.dto.request.ExchangeRate;

@Disabled
@DisplayName("외부 API 통신 테스트")
@ActiveProfiles("default")
@SpringBootTest
class WebClientExchangeRateAPICallerTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExchangeRateAPICaller exchangeRateAPICaller;

    @DisplayName("외부 API 정상 통신 테스트")
    @Test
    void exchangeRateAPICallerTest() {
        // given

        // when
        ExchangeRate exchangeRate = exchangeRateAPICaller.getExchangeRate();

        // then
        log.info(String.valueOf(exchangeRate.getRatio()));
        assertThat(exchangeRate.getRatio()).isGreaterThan(1000.0);
        assertThat(exchangeRate.getRatio()).isLessThan(2000.0);
    }


}
