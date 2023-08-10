package integration.helper;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class OrderHelper {

    public static ExtractableResponse<Response> addOrder() {
        return CommonRestAssuredUtils.post("/orders",
                                                 CommonRestAssuredUtils.LONG_EXPIRED_TOKEN);
    }

    public static Long addOrderAndGetId() {
        Integer orderId =  CommonRestAssuredUtils.post("/orders",
                CommonRestAssuredUtils.LONG_EXPIRED_TOKEN).body().jsonPath().get("id");
        return orderId.longValue();
    }

    public static ExtractableResponse<Response> getOrder(Long orderId) {
        return CommonRestAssuredUtils.get("/orders/{orderId}", orderId,
                                                 CommonRestAssuredUtils.LONG_EXPIRED_TOKEN);
    }

    public static ExtractableResponse<Response> getAllOrders() {
        return CommonRestAssuredUtils.get("/orders",
                CommonRestAssuredUtils.LONG_EXPIRED_TOKEN);
    }
}
