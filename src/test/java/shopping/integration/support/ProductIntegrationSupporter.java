package shopping.integration.support;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class ProductIntegrationSupporter {

    public static ExtractableResponse<Response> findProducts() {
        return RestAssured
                .given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/")
                .then().log().all().extract();
    }
}
