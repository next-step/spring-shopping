package shopping.infrastructure;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

class CurrencyLayerExchangeRateProviderTest {

    private MockWebServer mockServer;
    private WebClient webClient;

    @BeforeEach
    void setUp() throws IOException {
        mockServer = new MockWebServer();

        mockServer.start();

        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + mockServer.getPort())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Test
    @DisplayName("Timeout에 의해 실패하면 retry.")
    void retryWhenTimeout() throws IOException {
        mockServer.setDispatcher(new Dispatcher() {
            // given
            private int count = 0;

            @NotNull
            @Override
            public MockResponse dispatch(@NotNull final RecordedRequest request) throws InterruptedException {
                if (request.getPath().contains("live") && count < 2) {
                    count++;
                    sleep(1000);
                }
                return new MockResponse().setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody("{\n" +
                                "    \"success\": true,\n" +
                                "    \"source\": \"USD\",\n" +
                                "    \"quotes\": {\n" +
                                "        \"USDKRW\": 1300.0\n" +
                                "    }\n" +
                                "}");
            }
        });

        // when
        CurrencyLayerExchangeRateProvider provider = new CurrencyLayerExchangeRateProvider(webClient, null);

        // then
        assertThat(provider.getExchangeRate()).isEqualTo(1300.0D);
    }

    @Test
    @DisplayName("API 요청이 많아 106 코드 발생 시 retry.")
    void retryWhenServerBusy() throws IOException {
        // given
        mockServer.setDispatcher(new Dispatcher() {
            private int count = 0;

            @NotNull
            @Override
            public MockResponse dispatch(@NotNull final RecordedRequest request) throws InterruptedException {
                if (request.getPath().contains("live") && count < 2) {
                    count++;
                    return new MockResponse().setHeader("Content-Type", "application/json")
                            .setResponseCode(200)
                            .setBody("{\n" +
                                    "    \"success\": false,\n" +
                                    "    \"code\": 106\n" +
                                    "}");
                }
                return new MockResponse().setHeader("Content-Type", "application/json")
                        .setResponseCode(200)
                        .setBody("{\n" +
                                "    \"success\": true,\n" +
                                "    \"source\": \"USD\",\n" +
                                "    \"quotes\": {\n" +
                                "        \"USDKRW\": 1300.0\n" +
                                "    }\n" +
                                "}");
            }
        });

        // when
        CurrencyLayerExchangeRateProvider provider = new CurrencyLayerExchangeRateProvider(webClient, null);

        // then
        assertThat(provider.getExchangeRate()).isEqualTo(1300.0D);
    }


}