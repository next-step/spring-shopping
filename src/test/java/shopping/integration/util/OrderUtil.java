package shopping.integration.util;

import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class OrderUtil {

    public static long createOrder(String accessToken) {
        String location = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/orders")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");

        return Long.parseLong(location.split("/")[2]);
    }
}
