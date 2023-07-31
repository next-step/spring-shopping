package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DisplayName("상품 인수 테스트")
class ProductIntegrationTest extends IntegrationTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("성공 : 상품 목록 전체 조회")
    void findAllProducts() {
        /* given */

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .when().get("/")
            .then().log().all()
            .extract();

        /* then */
        final List<String> result = response.htmlPath()
            .getList("html.body.div.section.div.div.div.span");
        assertThat(result).containsAll(List.of("치킨", "피자", "사케"));
    }
}
