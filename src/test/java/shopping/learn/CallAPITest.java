package shopping.learn;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Disabled
@SpringBootTest
@DisplayName("외부 API 사용 학습 테스트")
class CallAPITest {

    @Value("${secret.currency.access_key}")
    String ACCESS_KEY;

    @Autowired
    WebClient webClient;

    @DisplayName("환율 api 통신 실험")
    @Test
    void currencyApiLearnTest() {
        // given
        String location =
                "http://api.currencylayer.com/live?currencies=KRW&access_key=" + ACCESS_KEY;
        WebClient client = WebClient.create();

        // when
        JsonNode response = client.get().uri(location)
                .accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        // then
        assertThat(response).isNotNull();
        System.out.println(response.get("quotes").get("USDKRW"));
    }

    @DisplayName("웹 클라이언트 빈 생성 후 환율 api 테스트")
    @Test
    void currencyApiLearnTestWithBean() {
        // given

        // when
        JsonNode response = webClient.get()
                .accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        // then
        assertThat(response).isNotNull();
        System.out.println(response);
        System.out.println(response.get("quotes").get("USDKRW"));
    }
}
