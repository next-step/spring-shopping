package shopping.auth.interceptor;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import shopping.auth.TokenProvider;
import shopping.exception.WooWaException;

@DisplayName("LoginTokenInterceptor 단위 테스트")
class LoginTokenInterceptorTest {

    public static final String LONG_EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjkxMDUxMTUyLCJleHAiOjkxNjkxMDUxMTUyfQ.AEu-Z9ndgW24b5M45dj6uSY3ZgY1JpSmB3S05wJZhwo";
    public static final String SECRET_KEY = "fjhbewhjbrfkelwdvhkewjkbwd";
    public static final long VALIDITY_IN_MILLISECONDS = 1000L;
    public static final String BEARER_TYPE = "Bearer";

    @Test
    @DisplayName("Authorization header에 토큰이 없는 경우 예외를 반환한다")
    void preHandleWhenNoToken() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        TokenProvider tokenProvider = new TokenProvider(SECRET_KEY, VALIDITY_IN_MILLISECONDS);
        LoginTokenInterceptor loginTokenInterceptor = new LoginTokenInterceptor(tokenProvider);

        assertThatThrownBy(() -> loginTokenInterceptor.preHandle(
            mockHttpServletRequest, mockHttpServletResponse, null))
            .isInstanceOf(WooWaException.class)
            .hasMessage("권한이 없습니다");
    }

    @Test
    @DisplayName("Authorization header에 토큰이 비정상인 경우 예외를 반환한다")
    void preHandleWhenInvalidToken() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, BEARER_TYPE + " " + "invalid token");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        TokenProvider tokenProvider = new TokenProvider(SECRET_KEY, VALIDITY_IN_MILLISECONDS);
        LoginTokenInterceptor loginTokenInterceptor = new LoginTokenInterceptor(tokenProvider);

        assertThatThrownBy(() -> loginTokenInterceptor.preHandle(
            mockHttpServletRequest, mockHttpServletResponse, null))
            .isInstanceOf(WooWaException.class)
            .hasMessage("권한이 없습니다");
    }

    @Test
    @DisplayName("Authorization header에 정상 토큰이 있는 경우 preHandle이 true를 반환한다.")
    void preHandle() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, BEARER_TYPE + " " + LONG_EXPIRED_TOKEN);
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        TokenProvider tokenProvider = new TokenProvider(SECRET_KEY, VALIDITY_IN_MILLISECONDS);
        LoginTokenInterceptor loginTokenInterceptor = new LoginTokenInterceptor(tokenProvider);

        boolean result = loginTokenInterceptor.preHandle(
            mockHttpServletRequest, mockHttpServletResponse, null);

        assertTrue(result);
    }

}
