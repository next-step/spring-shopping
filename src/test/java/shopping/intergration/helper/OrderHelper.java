package shopping.intergration.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import shopping.dto.response.OrderCreateResponse;

public class OrderHelper {

    public static ExtractableResponse<Response> createOrderRequest(final String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/orders")
                .then().log().all().extract();
    }

    public static OrderCreateResponse createOrders(final String accessToken) {
        return RestAssuredHelper.extractObject(RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/orders")
                .then().log().all().extract(), OrderCreateResponse.class);
    }

}

