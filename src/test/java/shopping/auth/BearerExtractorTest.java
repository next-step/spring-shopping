package shopping.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import shopping.global.exception.ShoppingAuthenticationException;
import shopping.auth.util.BearerExtractor;

@DisplayName("Bearer 토큰 관련 기능 테스트")
class BearerExtractorTest {
    BearerExtractor bearerExtractor;

    @BeforeEach
    void setUp() {
        this.bearerExtractor = new BearerExtractor();
    }

    @Test
    @DisplayName("토큰을 정상적으로 추출한다.")
    void extract() {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer 123");

        /* when */
        final String extract = bearerExtractor.extract(request);

        /* then */
        assertThat(extract).isEqualTo("123");
    }

    @Test
    @DisplayName("토큰을 파싱하는데 실패하면 ShoopingAuthenticationException을 던진다. ")
    void extractFailWithDoesNotParse() {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearerd123");
        /* when & then */
        assertThatCode(() -> bearerExtractor.extract(request))
            .isInstanceOf(ShoppingAuthenticationException.class)
            .hasMessage("토큰을 파싱하는데 실패하였습니다.");
    }


    @Test
    @DisplayName("토큰 헤더가 존재하지 않으면 ShoppingAuthenticationException을 던진다.")
    void extractFailWithDoesNotExistHeader() {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();

        /* when & then */
        assertThatCode(() -> bearerExtractor.extract(request))
            .isInstanceOf(ShoppingAuthenticationException.class)
            .hasMessage("토큰 헤더가 존재하지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("토큰 값이 존재하지 않으면 ShoppingAuthenticationException을 던진다.")
    void extractFailWithDoesNotExistValue(final String value) {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, value);

        /* when & then */
        assertThatCode(() -> bearerExtractor.extract(request))
            .isInstanceOf(ShoppingAuthenticationException.class)
            .hasMessage("토큰 값이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("토큰 값이 Bearer로 시작하지 않으면 ShoppingAuthenticationException을 던진다.")
    void extractFailWithDoesNotStartWithBearer() {
        /* given */
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bear 123");

        /* when & then */
        assertThatCode(() -> bearerExtractor.extract(request))
            .isInstanceOf(ShoppingAuthenticationException.class)
            .hasMessage("토큰이 Bearer로 시작하지 않습니다.");
    }
}
