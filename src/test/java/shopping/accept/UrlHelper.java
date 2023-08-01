package shopping.accept;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.MediaType;

class UrlHelper {

    static final class Product {

        public static ValidatableResponse getAllProducts() {
            return given().log().all()
                    .when().get("/")
                    .then().log().all();
        }

    }

    static final class Auth {

        public static ExtractableResponse<Response> login(LoginRequest loginRequest) {
            return given().log().all()
                    .body(loginRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/login/token")
                    .then().log().all()
                    .extract();
        }
    }
}
