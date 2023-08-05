package shopping.intergration.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class RestAssuredHelper {

    private static final String JSON_ROOT_PATH = "";

    public static <T> T extractObject(final ExtractableResponse<Response> response, Class<T> objectType) {
        return response.jsonPath().getObject(JSON_ROOT_PATH, objectType);
    }
}
