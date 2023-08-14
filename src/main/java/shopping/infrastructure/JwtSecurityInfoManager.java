package shopping.infrastructure;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.domain.SecurityInfo;
import shopping.domain.SecurityInfoManager;
import shopping.exception.TokenException;

import java.util.Date;

@Component
public class JwtSecurityInfoManager implements SecurityInfoManager {

    private static final long TOKEN_VALIDITY_MILLI = 60 * 60 * 1000;
    public static final String MEMBER_ID_KEY = "memberId";

    private final String secretKey;

    public JwtSecurityInfoManager(@Value("${shopping.token.secretKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String encode(SecurityInfo securityInfo) {
        Date date = new Date();

        return Jwts.builder()
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TOKEN_VALIDITY_MILLI))
                .claim(MEMBER_ID_KEY, securityInfo)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public SecurityInfo decode(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(MEMBER_ID_KEY, SecurityInfo.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenException("토큰이 유효하지 않습니다");
        }
    }
}
