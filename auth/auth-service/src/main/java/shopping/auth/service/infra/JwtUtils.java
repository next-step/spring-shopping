package shopping.auth.service.infra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.auth.app.domain.User;

@Component
public final class JwtUtils {

    private final String secretKey;
    private final long validityInMilliseconds;

    public JwtUtils(@Value("${security.jwt.token.secret-key:testKey}") String secretKey,
            @Value("${security.jwt.token.expire-length:3000000}") long validityInMilliseconds) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String generate(final User user) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId()));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String payload(final String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.substring(7)).getBody().getSubject();
    }
}
