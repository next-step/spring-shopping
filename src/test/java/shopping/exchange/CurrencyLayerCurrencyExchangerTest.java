package shopping.exchange;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonTest
@Import({CurrencyLayerCurrencyExchanger.class, CurrencyLayerParser.class})
class CurrencyLayerCurrencyExchangerTest {

    @MockBean
    CurrencyLayerClient currencyLayerClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CurrencyLayerCurrencyExchanger currencyLayerCurrencyExchanger;

    @Test
    @DisplayName("USD에서 KRW로 바꾸는 환율을 가져온다.")
    void findCurrencyExchangeRateFromUsdToKrw() {
        /* given */
        final JsonNode body = objectMapper.createObjectNode()
            .put("success", true)
            .set("quotes", objectMapper.createObjectNode().put("USDKRW", 1234.5));
        Mockito.when(currencyLayerClient.sendLiveCurrencyExchangeRateRequest())
            .thenReturn(new ResponseEntity<>(body, HttpStatus.OK));

        /* when */
        final Optional<CurrencyExchangeRate> rate = currencyLayerCurrencyExchanger
            .findCurrencyExchangeRate(CurrencyType.USD, CurrencyType.KRW);

        /* then */
        assertThat(rate).hasValue(new CurrencyExchangeRate(1234.5));
    }
}
