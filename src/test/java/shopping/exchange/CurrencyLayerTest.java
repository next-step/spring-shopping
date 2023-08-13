package shopping.exchange;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.config.RestConfig;

@ExtendWith(SpringExtension.class)
@Import({CurrencyLayerClient.class, RestConfig.class})
class CurrencyLayerTest {

    @Autowired
    CurrencyLayerClient client;

    @Disabled("실제 API 응답 결과가 궁금할 때만 사용한다.")
    @Test
    @DisplayName("CurrencyLayer에서 환율 정보를 가져온다.")
    void sendLiveCurrencyExchangeRateRequest() {
        /* given */

        /* when */
        final ResponseEntity<JsonNode> response = client.sendLiveCurrencyExchangeRateRequest();

        /* then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        System.out.println(response.getBody().toPrettyString());
    }

}
