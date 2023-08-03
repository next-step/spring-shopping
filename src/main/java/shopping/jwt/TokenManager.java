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

    private final String secretKey;

    public TokenManager(@Value("${shopping.token.secretKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(Long id) {
        Date date = new Date();

        return Jwts.builder()
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TOKEN_VALIDITY_MILLI))
                .claim(MEMBER_ID_KEY, id)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Long decodeToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(MEMBER_ID_KEY, Long.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenException("토큰이 유효하지 않습니다");
        }
    }
}
