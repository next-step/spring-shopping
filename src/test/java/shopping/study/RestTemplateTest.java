package shopping.study;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@DisplayName("RestTemplate의 사용법을 공부한다.")
public class RestTemplateTest {

    @Value(value = "${currency.accessKey}")
    private String accessKey;

    @Test
    @DisplayName("RestTemplate Client를 이용해 호출하고 반환값을 검증한다")
    void RestTemplate() {
        // given
        RestTemplatePractice restTemplatePractice = new RestTemplatePractice(accessKey);
        // when
        ResponseEntity<Map> body = restTemplatePractice.getBody();
        // then
        assertThat(body.getBody().get("success")).isEqualTo(true);
    }


    static class RestTemplatePractice {

        final String accessKey;
        final String url;
        final RestTemplate restTemplate;

        public RestTemplatePractice(@Value("${currency.accessKey}") String accessKey) {
            this.accessKey = accessKey;
            this.url = "http://api.currencylayer.com/live?currencies=KRW&access_key=" + accessKey;
            this.restTemplate = new RestTemplate();
        }

        public ResponseEntity<Map> getBody() {
            return restTemplate.getForEntity(url, Map.class);
        }

    }

}
