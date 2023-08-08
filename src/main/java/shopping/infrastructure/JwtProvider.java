package shopping.infrastructure;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider implements TokenProvider {

    @Value("${security.jwt.token.secret-key.value}")
    private String secretKey;

    @Value("${security.jwt.token.secret-key.version}")
    private String secretKeyVersion;

    @Value("${security.jwt.token.expire-length}")
    private long expireLength;

    public String create(final String payload) {
        final Map<String, Object> verClaim = new HashMap<>(Map.of("ver", secretKeyVersion));
        final Claims claims = Jwts.claims(verClaim)
                .setSubject(payload);

        final Date now = new Date();
        final Date expire = new Date(now.getTime() + expireLength);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPayload(final String token) {
        return getClaims(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(final String token) {
        try {
            final Jws<Claims> claims = getClaims(token);
            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (final JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> getClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }
}
