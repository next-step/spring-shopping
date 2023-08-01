package shopping.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtProviderTest {

    JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(
            "66eI65287YOV64SI66y066eb7J6I7Ja07JqU66eo64Kg66i57J2E7IiY7J6I7Ja0", 3600000
        );
    }

    @Test
    @DisplayName("id와 email 정보를 통해, jwt 토큰 생성")
    void createJwt() {
        /* given */
        final Long memberId = 1L;

        /* when */
        final String accessToken = jwtProvider.createToken(memberId);

        /* then */
        assertThat(accessToken).isNotEmpty();
        System.out.println("accessToken = " + accessToken);
    }
}
