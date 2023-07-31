package shopping.accept;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import org.springframework.http.MediaType;

class UrlHelper {

    static final class Product {

        public static ValidatableResponse getAllProducts() {
            return given().log().all().accept(MediaType.TEXT_HTML_VALUE)
                    .when().log().all().get("/products")
                    .then().log().all();
        }

    }

}
