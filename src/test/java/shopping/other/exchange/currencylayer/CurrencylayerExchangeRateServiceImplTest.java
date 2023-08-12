package shopping.other.exchange.currencylayer;


import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.common.domain.Rate;
import shopping.order.service.ExchangeType;

@SpringBootTest
class CurrencylayerExchangeRateServiceImplTest {

    private static MockWebServer server;
    private CurrencylayerExchangeRateServiceImpl exchangeRateService;
    @Autowired
    private WebClient webClient;
    @Value("${currencylayer.url}")
    private String currencylayerUrl;


    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.start(1913);
    }

    @BeforeEach
    void beforeEach() {
        exchangeRateService = new CurrencylayerExchangeRateServiceImpl(webClient, new ObjectMapper(), "apikey", currencylayerUrl);
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    @DisplayName("미국과 한국의 환률을 가져온다")
    void getExchangeRate() {
        //given
        String jsonString = getSuccessJson();

        MockResponse successMockResponse = new MockResponse()
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .setResponseCode(HttpStatus.OK.value())
            .setBody(jsonString);
        server.enqueue(successMockResponse);

        Rate exchangeRate = exchangeRateService.getExchangeRate(ExchangeType.USD, ExchangeType.KRW);

        assertThat(exchangeRate).isEqualTo(new Rate(1300));
    }

    @Test
    @DisplayName("환율을 가져올 때 3번 실패하고 가져오기에 성공한다")
    void getExchangeRate3times() {
            //given
            String jsonString = getSuccessJson();
            MockResponse failMockResponse = new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            MockResponse successMockResponse = new MockResponse()
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(HttpStatus.OK.value())
                .setBody(jsonString);

            server.enqueue(failMockResponse);
            server.enqueue(failMockResponse);
            server.enqueue(successMockResponse);

            //when
            Rate exchangeRate = exchangeRateService.getExchangeRate(ExchangeType.USD, ExchangeType.KRW);

            //then
            assertThat(exchangeRate).isEqualTo(new Rate(1300));
    }

    @Test
    @DisplayName("환율을 가져올 때 3번 timeout 발생 시 emtry Rate을 반환한다")
    void getEmptyRate3timesTimeout() {
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));
        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));

        Rate exchangeRate = exchangeRateService.getExchangeRate(ExchangeType.USD, ExchangeType.KRW);

        assertThat(exchangeRate).isEqualTo(Rate.EMPTY_RATE);
    }


    @NotNull
    private String getSuccessJson() {
        return "{\n"
            + "\"timestamp\": 1691770329,\n"
            + "\"source\": \"USD\",\n"
            + "\"quotes\": {\n"
            + "\"USDKRW\": 1300\n"
            + "}\n"
            + "}";
    }


}
