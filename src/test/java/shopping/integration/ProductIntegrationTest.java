package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("상품 통합 테스트")
class ProductIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("전체 상품 리스트를 조회한다.")
    void findAll() {
        // given
        // data.sql

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .get("/")
            .then().log().all()
            .assertThat()
            .contentType(ContentType.HTML)
            .statusCode(200)
            .extract();

        // then
        String html = response.htmlPath().getString("body");
        assertThat(html).contains("피자", "치킨", "햄버거", "15000.00", "35000.00", "5000.00");
    }

}
