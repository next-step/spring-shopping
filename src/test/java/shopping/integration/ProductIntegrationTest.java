package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ProductIntegrationTest extends IntegrationTest {

    @DisplayName("상품 목록 페이지를 조회한다.")
    @Test
    void getAllProduct() {
        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
