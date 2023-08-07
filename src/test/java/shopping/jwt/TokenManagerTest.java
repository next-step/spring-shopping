package shopping.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.exception.TokenException;

@DisplayName("TokenManager 클래스")
class TokenManagerTest {

    private static final String TEST_SECRET_KEY = "this is test secret key";
    private static final int TEST_TOKEN_VALIDITY_MILLI = 60 * 60 * 1000;

    private TokenManager tokenManager = new TokenManager(TEST_SECRET_KEY);

    @DisplayName("createToken 메서드는")
    @Nested
    class CreateToken_Method {

        @DisplayName("비밀키로 사인된 jwt 토큰을 생성한다.")
        @Test
        void returnJwtToken() {
            // given
            Long memberId = 1L;

            // when
            String jwt = tokenManager.createToken(memberId);

            // then
            assertThat(jwt).isNotBlank();
        }
    }

    @DisplayName("decodeToken 메서드는")
    @Nested
    class DecodeToken_Method {

        @DisplayName("비밀키로 사인된 jwt 토큰으로부터 memberId 를 반환한다.")
        @Test
        void returnMemberIdFromJwt() {
            // given
            Long memberId = 1L;
            String jwt = tokenManager.createToken(memberId);

            // when
            Long decodedMemberId = tokenManager.decodeToken(jwt);

            // then
            assertThat(decodedMemberId).isEqualTo(memberId);
        }

        @DisplayName("유효하지 않은 jwt 토큰 해독을 시도하면 TokenException 을 던진다.")
        @Test
        void throwTokenException_WhenJwtTokenIsMalFormed() {
            // given
            String jwt = "malformed jwt token";

            // when
            Exception exception = catchException(() -> tokenManager.decodeToken(jwt));

            // then
            assertThat(exception).isInstanceOf(TokenException.class);
            assertThat(exception.getMessage()).isEqualTo("토큰이 유효하지 않습니다");
        }

        @DisplayName("jwt 토큰이 null 이면 TokenException 을 던진다.")
        @Test
        void throwTokenException_WhenJwtTokenIsNull() {
            // given
            String jwt = null;

            // when
            Exception exception = catchException(() -> tokenManager.decodeToken(jwt));

            // then
            assertThat(exception).isInstanceOf(TokenException.class);
            assertThat(exception.getMessage()).isEqualTo("토큰이 유효하지 않습니다");
        }

        @DisplayName("사인된 비밀키가 다르면 TokenException 을 던진다.")
        @Test
        void throwTokenException_WhenDifferentJwtSignature() {
            // given
            Long memberId = 1L;
            String differentSecretKey = "different";
            String jwt = createTestToken(differentSecretKey, new Date(), memberId);

            // when
            Exception exception = catchException(() -> tokenManager.decodeToken(jwt));

            // then
            assertThat(exception).isInstanceOf(TokenException.class);
            assertThat(exception.getMessage()).isEqualTo("토큰이 유효하지 않습니다");
        }

        @DisplayName("토큰이 만료되면 TokenException 을 던진다.")
        @Test
        void throwTokenException_WhenExpiredJwt() {
            // given
            Long memberId = 1L;
            Date expiredDate = new Date(new Date().getTime() - TEST_TOKEN_VALIDITY_MILLI);
            String jwt = createTestToken(TEST_SECRET_KEY, expiredDate, memberId);

            // when
            Exception exception = catchException(() -> tokenManager.decodeToken(jwt));

            // then
            assertThat(exception).isInstanceOf(TokenException.class);
            assertThat(exception.getMessage()).isEqualTo("토큰이 유효하지 않습니다");
        }
    }

    private String createTestToken(String secretKey, Date issuedAt, Long memberId) {
        return Jwts.builder()
            .setIssuedAt(issuedAt)
            .setExpiration(new Date(issuedAt.getTime() + TEST_TOKEN_VALIDITY_MILLI))
            .claim("memberId", memberId)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }
}