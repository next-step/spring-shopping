package shopping.acceptance;

import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@DisplayName("상품 관련 기능 인수 테스트")
class ProductAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("모든 상품 목록을 포함하는 메인 페이지 HTML을 받는다.")
    void getMainPage() {
        // TODO: HTML 테스트
        RestAssured
            .given().log().all()
            .accept(MediaType.TEXT_HTML_VALUE)
            .when().get("")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .body(containsString("피자"));
    }
}
