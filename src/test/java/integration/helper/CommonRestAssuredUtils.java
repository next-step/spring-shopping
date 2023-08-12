package integration.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.springframework.http.MediaType;

public class CommonRestAssuredUtils {

    // 2400년이 만료 기한이고 PK가 1L인 유저 정보를 담고있는 테스트 용 토큰 정보
    public static final String DEFAULT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjkxMDUxMTUyLCJleHAiOjkxNjkxMDUxMTUyfQ.AEu-Z9ndgW24b5M45dj6uSY3ZgY1JpSmB3S05wJZhwo";

    public static ExtractableResponse<Response> get(String url) {
        return RestAssured.given().log().all()
            .when()
            .get(url)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> get(String url, String token) {
        return RestAssured.given().log().all()
            .header("Authorization", "Bearer " + token)
            .when()
            .get(url)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> get(String url, T pathParam) {
        return RestAssured
            .given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get(url, pathParam)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> get(String url, T pathParam, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get(url, pathParam)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> get(String url, Map<String, T> params) {
        return RestAssured
            .given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .params(params)
            .when().get(url)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> post(String url, T body) {
        return RestAssured.given().log().all()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(url)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> post(String url, T pathParam, T body) {
        return RestAssured.given().log().all()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(url, pathParam)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> post(String url, T body, String token) {
        return RestAssured.given().log().all()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + token)
            .when()
            .post(url)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> delete(String url) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete(url)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> delete(String url, T pathParam) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete(url, pathParam)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> delete(String url, T pathParam, String token) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + token)
            .when()
            .delete(url, pathParam)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> put(String url, T body) {
        return RestAssured.given().log().all()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put(url)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> put(String url, T pathParam, T body) {
        return RestAssured.given().log().all()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put(url, pathParam)
            .then().log().all()
            .extract();
    }

    public static <T> ExtractableResponse<Response> patch(String url, T pathParam, T body, String token) {
        return RestAssured.given().log().all()
            .body(body)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + token)
            .when()
            .patch(url, pathParam)
            .then().log().all()
            .extract();
    }


}
