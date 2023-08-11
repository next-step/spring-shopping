package shopping.auth;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtResolverTest {

    static final int ONE_HOUR = 3600000;
    static final String secret = "66eI65287YOV64SI66y066eb7J6I7Ja07JqU66eo64Kg66i57J2E7IiY7J6I7Ja0";

    JwtResolver jwtResolver;

    @BeforeEach
    void setUp() {
        jwtResolver = new JwtResolver(secret);
    }

    @Test
    @DisplayName("Jwt에서 Subject claim 값을 가져온다.")
    void getSubject() {
        /* given */
        final String jwt = generateToken("1");

        /* when */
        final String subject = jwtResolver.getSubject(jwt);

        /* then */
        assertThat(subject).isEqualTo("1");
    }

    @Test
    @DisplayName("Jwt가 만료되었는지 확인할 수 있다.")
    void validateToken() {
        /* given */
        final String jwt = generateToken("777");
        final String expired = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjkxMDI5NjExLCJleHAiOjE2OTEwMjk2MTF9.g_8Dw3NjGGUz9Jn1jN5U-y2SL-a8nmVQeMr17d0NLjw";

        /* when & then */
        assertThat(jwtResolver.validateToken(jwt)).isTrue();
        assertThat(jwtResolver.validateToken(expired)).isFalse();
    }

    private String generateToken(final String subject) {
        final Date now = new Date();
        final Date expired = new Date(now.getTime() + ONE_HOUR);

        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expired)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }
}
