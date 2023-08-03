package shopping.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.domain.User;

@Component
public class TokenProvider {

    private static final String EMAIL = "email";
    private static final int START_INDEX_WITHOUT_BEARER = 7;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private long expiration;

    public String issueToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(EMAIL, user.getEmail());
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + this.expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public String getEmail(String token) {
        String tokenWithOutBearer = token.substring(START_INDEX_WITHOUT_BEARER);
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(tokenWithOutBearer)
                .getBody()
                .get(EMAIL, String.class);
    }

}
