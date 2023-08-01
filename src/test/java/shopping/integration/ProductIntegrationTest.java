package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import shopping.integration.config.IntegrationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class ProductIntegrationTest {
    @Test
    @DisplayName("메인에서 상품 목록을 조회할 수 있다.")
    void findAllProducts() {
        // when
        Response response = RestAssured.given().log().all()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .when().get("/")
                .then()
                .statusCode(200)
                .extract().response();

        List<String> images = response.htmlPath().getList("html.body.div.section.div.img.@src");
        List<String> names = response.htmlPath().getList("**.findAll {it.@class == 'product-info__name'}");
        List<String> prices = response.htmlPath().getList("**.findAll {it.@class == 'product-info__price'}");

        // then
        assertThat(images).contains("/assets/images/chicken.png", "/assets/images/pizza.png");
        assertThat(names).contains("치킨", "피자");
        assertThat(prices).contains("20000", "25000");
    }
}
