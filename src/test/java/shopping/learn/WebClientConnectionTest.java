package shopping.learn;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.dto.response.ExchangeRateResponse;

import static org.assertj.core.api.Assertions.assertThat;


@Disabled
@SpringBootTest
class WebClientConnectionTest {

    @Value("${security.currency-layer.access-key}")
    private String accessKey;


    @DisplayName("WebClient 생성 및 API 연결시 응답 확인")
    @Test
    void connect() {
        WebClient client = WebClient.create("http://apilayer.net/api/live");
        JsonNode jsonNode = client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("access_key", accessKey)
                        .queryParam("currencies", "KRW")
                        .queryParam("source", "USD")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        assertThat(jsonNode).isNotNull();
        System.out.println(jsonNode);
    }

    @DisplayName("API 응답을 DTO 객체로 변환 후 확인")
    @Test
    void toDTO() {
        WebClient client = WebClient.create("http://apilayer.net/api/live");
        ExchangeRateResponse exchangeRateResponse = client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("access_key", accessKey)
                        .queryParam("currencies", "KRW")
                        .queryParam("source", "USD")
                        .build())
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .block();
        assertThat(exchangeRateResponse).isNotNull();
        assertThat(exchangeRateResponse.getQuotes().get("USDKRW")).isCloseTo(1300L, Offset.offset(100L));
    }
}

