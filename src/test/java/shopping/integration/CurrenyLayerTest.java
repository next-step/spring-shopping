package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("CurrencyLayerAPI Test")
public class CurrenyLayerTest {

    @Test
    @DisplayName("성공 : API 호출")
    void callCurrencyAPI() {
        ExtractableResponse<Response> response = RestAssured
            .given()
            .log().all()
            .when()
            .get("http://apilayer.net/api/live?access_key=df19208b7579412eee5bae3132cad09b&currencies=KRW&source=USD")
            .then()
            .log().all().extract();

        System.out.println("response = " + response);
    }
}
