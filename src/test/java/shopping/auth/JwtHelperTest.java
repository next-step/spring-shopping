package shopping.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.auth.util.JwtHelper;

@DisplayName("JWT 관련 기능 테스트")
class JwtHelperTest {
    
    JwtHelper jwtHelper;

    @BeforeEach
    void setUp() {
        jwtHelper = new JwtHelper(
            "66eI65287YOV64SI66y066eb7J6I7Ja07JqU66eo64Kg66i57J2E7IiY7J6I7Ja0", 3600000
        );
    }

    @Test
    @DisplayName("id와 email 정보를 통해, jwt 토큰 생성")
    void createJwt() {
        /* given */
        final Long memberId = 1L;

        /* when */
        final String accessToken = jwtHelper.createToken(memberId);

        /* then */
        assertThat(accessToken).isNotEmpty();
    }

    @Test
    @DisplayName("Jwt에서 Subject claim 값을 가져온다.")
    void getSubject() {
        /* given */
        final String jwt = jwtHelper.createToken(1L);

        /* when */
        final String subject = jwtHelper.getSubject(jwt);

        /* then */
        assertThat(subject).isEqualTo("1");
    }

    @Test
    @DisplayName("Jwt가 만료되었는지 확인할 수 있다.")
    void validateToken() {
        /* given */
        final String jwt = jwtHelper.createToken(1L);
        final String expired = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjkxMDI5NjExLCJleHAiOjE2OTEwMjk2MTF9.g_8Dw3NjGGUz9Jn1jN5U-y2SL-a8nmVQeMr17d0NLjw";

        /* when & then */
        assertThat(jwtHelper.validateToken(jwt)).isTrue();
        assertThat(jwtHelper.validateToken(expired)).isFalse();
    }
}
