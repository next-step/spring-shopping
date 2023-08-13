package shopping.infrastructure;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import shopping.exception.ShoppingException;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class USDExchangeRateServiceTest {

    @Autowired
    private USDExchangeRateService usdExchangeRateApi;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8888);
    }

    @AfterEach
    void cleanup() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("환율 정보를 가져오는 테스트")
    void USDExchangeRateApi() {
        String jsonStr = "{\"quotes\" : { \"USDKRW\" : 1300.00}}";
        LocalDateTime time = LocalDateTime.of(20013, 9, 10, 10, 30);
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(jsonStr));

        final double result = usdExchangeRateApi.getExchangeRateEveryMinute(time);
        assertThat(result).isCloseTo(1300.0, within(0.001));
    }

    @Test
    @DisplayName("환율 정보를 가져오는데 실패하면 최대 3번까지 재시도하고 3 번째에 성공한다.")
    void USDExchangeRateApiRetry() {
        String jsonStr = "{\"quotes\" : { \"USDKRW\" : 1300.00}}";
        LocalDateTime time = LocalDateTime.of(2012, 10, 12, 10, 30);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(jsonStr));

        final double result = usdExchangeRateApi.getExchangeRateEveryMinute(time);
        assertThat(result).isCloseTo(1300.0, within(0.001));
    }

    @Test
    @DisplayName("환율 정보를 가져오는데 실패하면 최대 3번까지 재시도하고 그래도 실패하면 예외를 던진다.")
    void USDExchangeRateApiRetryThenFail() {
        LocalDateTime time = LocalDateTime.of(2022, 11, 25, 10, 30);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

        assertThatCode(() -> usdExchangeRateApi.getExchangeRateEveryMinute(time))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("환율 정보를 가져오지 못했습니다.");
    }

    @Test
    @DisplayName("환율 정보를 가져오는데 캐시되어 있다면 요청 없이 환율 정보를 가져온다.")
    void USDExchangeRateApiRetryUsingCache() {
        LocalDateTime time = LocalDateTime.of(2021, 12, 20, 10, 30);
        LocalDateTime next = LocalDateTime.of(2021, 12, 20, 10, 30, 30);
        String jsonStr = "{\"quotes\" : { \"USDKRW\" : 1330.00}}";

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(jsonStr));

        final double result = usdExchangeRateApi.getExchangeRateEveryMinute(time);
        assertThat(result).isCloseTo(1330.0, within(0.001));

        final double cachedResult = usdExchangeRateApi.getExchangeRateEveryMinute(next);
        assertThat(cachedResult).isCloseTo(1330.0, within(0.001));
    }
}
