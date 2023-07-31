package shopping.accept;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;

class UrlHelper {

    static final class Product {

        public static ValidatableResponse getAllProducts() {
            return given().log().all()
                    .when().get("/")
                    .then().log().all();
        }

    }

}
