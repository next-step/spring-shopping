package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import shopping.integration.config.IntegrationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@Sql("/test-data.sql")
public class ProductIntegrationTest {
    @Test
    @DisplayName("메인에서 상품 목록을 조회할 수 있다.")
    void findAllProducts() {
        Response response = RestAssured.given().log().all()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .when().get("/")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.getBody().prettyPrint());
        List<String> images = response.htmlPath().getList("html.body.div.section.div.img.@src");
        List<String> names = response.htmlPath().getList("html.body.div.section.div.div.div");

        assertThat(images).contains("/chicken.png", "/pizza.png");
        assertThat(names).contains("치킨20000", "피자25000");
    }
}
