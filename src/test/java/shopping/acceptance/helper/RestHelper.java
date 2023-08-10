package shopping.acceptance.helper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RestHelper {

    private static final String BEARER = "Bearer ";

    private RestHelper() {
        throw new UnsupportedOperationException();
    }

    public static ExtractableResponse<Response> post(
        final String path,
        final String jwt
    ) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, BEARER + jwt)
            .when().post(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> post(
        final String path,
        final String jwt,
        final Object request
    ) {

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, BEARER + jwt)
            .body(request)
            .when().post(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> post(
        final String path,
        final Object request
    ) {

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> get(final String path, final String jwt) {

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, BEARER + jwt)
            .when().get(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> patch(
        final String path,
        final String jwt,
        final Object request,
        final List<Object> pathParam
    ) {

        return
            RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + jwt)
                .when().patch(path, pathParam.toArray())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> delete(
        final String path,
        final String jwt,
        final List<Long> pathParams
    ) {

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, BEARER + jwt)
            .when().delete(path, pathParams.toArray())
            .then().log().all()
            .extract();
    }
}
