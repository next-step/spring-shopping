package shopping.study;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest
@Disabled
class CurrencyLayerTest {

    private static final String API_URL = "http://apilayer.net/api/live";

    private final String accessKey;

    public CurrencyLayerTest(@Value("${currency-layer.access-key}") final String accessKey) {
        this.accessKey = accessKey;
    }

    @Test
    @DisplayName("실시간 환율을 조회한다")
    void findExchangeRate() {
        RestAssured
                .given()
                .baseUri(API_URL)
                .param("access_key", accessKey)
                .param("currencies", "KRW")
                .param("source", "USD")
                .param("format", 1)

                .when()
                .get()

                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("quotes", hasKey("USDKRW"));
    }
}
