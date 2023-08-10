package shopping.exchange;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import shopping.global.exception.ShoppingException;
import shopping.util.CurrencyLayerExchangeRateApi;
import shopping.util.ExchangeRateApi;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
public class CurrencyLayerExchangeRateApiTest {

    @Value("${currency.baseUrl}")
    private String url;
    @Value("${currency.accessKey}")
    private String accessKey;

    @Test
    @DisplayName("정상 동작 확인")
    void exchangeAPI_정상_동작_확인() {
        // given
        ExchangeRateApi exchangeRateApi = new CurrencyLayerExchangeRateApi(accessKey, url,
            new RestTemplate());
        // when & then
        assertThatCode(exchangeRateApi::callExchangeRate)
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("잘못된 url 일시 에러 반환")
    void exchangeAPI_잘못된_urL_에러() {
        // given
        ExchangeRateApi exchangeRateApi = new CurrencyLayerExchangeRateApi(
            accessKey,
            url + "1",
            new RestTemplate()
        );

        // when & then
        assertThatCode(() -> exchangeRateApi.callExchangeRate())
            .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("잘못된 accessKey 일시, 에러 반환")
    void exchangeAPI_잘못된_accessKey_에러() {
        // given
        ExchangeRateApi exchangeRateApi = new CurrencyLayerExchangeRateApi(
            accessKey + "1",
            url,
            new RestTemplate()
        );

        // when & then
        assertThatCode(() -> exchangeRateApi.callExchangeRate())
            .isInstanceOf(ShoppingException.class)
            .hasMessage("ExchangeAPI json가 존재하지 않습니다.");
    }
}
