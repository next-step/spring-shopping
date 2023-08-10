package study;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
public class RestClientTest {

    @Value(value = "${currency.accessKey}")
    private String accessKey;
    
    @Test
    @DisplayName("RestJongha 클래스를 호출한다. 쉬고싶어요..")
    void RestTemplate() {
        // given
        RestJongha restJongha = new RestJongha(accessKey);
        // when
        ResponseEntity<Map> body = restJongha.getBody();
        // then
        assertThat(body.getBody().get("success")).isEqualTo(true);
    }


    static class RestJongha {

        final String accessKey;
        final String url;
        final RestTemplate restTemplate;

        public RestJongha(@Value("${currency.accessKey}") String accessKey) {
            System.out.println("asds" + accessKey);
            this.accessKey = accessKey;
            this.url = "http://api.currencylayer.com/live?currencies=KRW&access_key=" + accessKey;
            this.restTemplate = new RestTemplate();
        }

        public ResponseEntity<Map> getBody() {
            return restTemplate.getForEntity(url, Map.class);
        }

    }

}
