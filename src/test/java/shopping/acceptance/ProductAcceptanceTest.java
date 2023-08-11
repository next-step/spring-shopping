package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@DisplayName("상품 관련 기능 인수 테스트")
class ProductAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("모든 상품 목록을 포함하는 메인 페이지 HTML을 받는다.")
    void getMainPage() {
        /* given */


        /* when */
        final XmlPath xmlPath = RestAssured
            .given().log().all()
            .accept(MediaType.TEXT_HTML_VALUE)
            .when().get("")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().htmlPath();

        /* then */
        assertThat(xmlPath.getString("html.body.div.section.div[0].div.div.span[0]"))
            .isEqualTo("치킨");
        assertThat(xmlPath.getObject("html.body.div.section.div[0].div.div.span[1]", Integer.class))
            .isEqualTo(20000);
        assertThat(xmlPath.getString("html.body.div.section.div[1].div.div.span[0]"))
            .isEqualTo("햄버거");
        assertThat(xmlPath.getObject("html.body.div.section.div[1].div.div.span[1]", Integer.class))
            .isEqualTo(10000);
        assertThat(xmlPath.getString("html.body.div.section.div[2].div.div.span[0]"))
            .isEqualTo("피자");
        assertThat(xmlPath.getObject("html.body.div.section.div[2].div.div.span[1]", Integer.class))
            .isEqualTo(20000);
    }
}
