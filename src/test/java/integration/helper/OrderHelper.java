package integration.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apiguardian.api.API;

public class OrderHelper {

    private static String API_URL = "/api";

    public static ExtractableResponse<Response> addOrder() {
        return CommonRestAssuredUtils.post(API_URL + "/orders",
                                                 CommonRestAssuredUtils.LONG_EXPIRED_TOKEN);
    }

    public static Long addOrderAndGetId() {
        Integer orderId =  CommonRestAssuredUtils.post(API_URL + "/orders",
                CommonRestAssuredUtils.LONG_EXPIRED_TOKEN).body().jsonPath().get("id");
        return orderId.longValue();
    }

    public static ExtractableResponse<Response> getOrder(Long orderId) {
        return CommonRestAssuredUtils.get(API_URL + "/orders/{orderId}", orderId,
                                                 CommonRestAssuredUtils.LONG_EXPIRED_TOKEN);
    }

    public static ExtractableResponse<Response> getAllOrders() {
        return CommonRestAssuredUtils.get(API_URL + "/orders",
                CommonRestAssuredUtils.LONG_EXPIRED_TOKEN);
    }
}
