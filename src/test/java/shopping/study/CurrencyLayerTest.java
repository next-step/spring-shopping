package shopping.study;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest
class CurrencyLayerTest {

    private static final String API_URL = "http://api.currencylayer.com/live";

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

                .when()
                .get()

                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true))
                .body("quotes", hasKey("USDKRW"));
    }
}
