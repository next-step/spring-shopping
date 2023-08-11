package shopping.accept;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import shopping.auth.domain.usecase.request.LoginRequest;
import shopping.mart.domain.usecase.cart.request.CartAddRequest;
import shopping.mart.domain.usecase.cart.request.CartUpdateRequest;

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

    static final class Cart {

        static ExtractableResponse<Response> addProduct(CartAddRequest cartAddRequest, String accessToken) {
            return given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                    .body(cartAddRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/carts")
                    .then().log().all()
                    .extract();
        }

        static ExtractableResponse<Response> findCart(String accessToken) {
            return given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/carts")
                    .then().log().all()
                    .extract();
        }

        static ExtractableResponse<Response> updateCart(CartUpdateRequest cartUpdateRequest, String accessToken) {
            return given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .body(cartUpdateRequest)
                    .when().patch("/carts")
                    .then().log().all()
                    .extract();
        }

        public static ExtractableResponse<Response> deleteCart(long deleteTargetId, String accessToken) {
            return given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("product-id", deleteTargetId)
                    .when().delete("/carts")
                    .then().log().all()
                    .extract();
        }
    }

    static final class Order {

        static ExtractableResponse<Response> orderCart(String accessToken) {
            return given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/orders")
                    .then().log().all()
                    .extract();
        }
    }

    static final class Receipt {

        static ExtractableResponse<Response> history(String accessToken) {
            return given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/receipts")
                    .then().log().all()
                    .extract();
        }

        static ExtractableResponse<Response> details(long receiptId, String accessToken) {
            return given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/receipts/{receipt-id}", receiptId)
                .then().log().all()
                .extract();
        }
    }
}
