package shopping.other.exchange.currencylayer;


import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.common.domain.Rate;
import shopping.order.service.ExchangeType;

@TestPropertySource(locations = "classpath:application.properties")
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

        String jsonString = "{\n"
            + "\"timestamp\": 1691770329,\n"
            + "\"source\": \"USD\",\n"
            + "\"quotes\": {\n"
            + "\"USDKRW\": 1300\n"
            + "}\n"
            + "}";

        MockResponse mockResponse = new MockResponse()
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .setResponseCode(HttpStatus.OK.value())
            .setBody(jsonString);

        server.enqueue(mockResponse);

        System.out.println(exchangeRateService.getClass());
        Rate exchangeRate = exchangeRateService.getExchangeRate(ExchangeType.USD, ExchangeType.KRW);

        assertThat(exchangeRate).isEqualTo(new Rate(1300));
    }
}
