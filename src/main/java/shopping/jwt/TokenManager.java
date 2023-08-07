package shopping.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.exception.TokenException;

import java.util.Date;

@Component
public class TokenManager {

    private static final long TOKEN_VALIDITY_MILLI = 60 * 60 * 1000;
    public static final String MEMBER_ID_KEY = "memberId";

    @Value("${shopping.token.secretKey}")
    private String SECRET_KEY;

    public String createToken(Long id) {
        Date date = new Date();

        return Jwts.builder()
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TOKEN_VALIDITY_MILLI))
                .claim(MEMBER_ID_KEY, id)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Long decodeToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(MEMBER_ID_KEY, Long.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenException("토큰이 유효하지 않습니다");
        }
    }
}
