package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.acceptance.helper.AuthHelper;
import shopping.acceptance.helper.RestHelper;

public class OrderAcceptanceTest extends AcceptanceTest {
    @Test
    @DisplayName("사용자가 장바구니의 상품을 주문한다")
    void 사용자가_장바구니의_상품을_주문_한다(){
        // 사용자가 로그인을 한다
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        // 상품을 장바구니에 담는다
        // 주문을 한다.
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .when().post("/api/order")
            .then().log().all()
            .extract();

        // 현재 cartProduct에서 cartProduct들을 조회한다.
        // Order를 만든다.
        // order에는 여러 OrderItem이 있는데, 이를 넣는다.
        // 주문을 다하면, 주문 상세 페이지로 넘어간다.
        // 주문을 다하면 , response는 ok이다.
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}