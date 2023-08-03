package shopping.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long expirationMilliSeconds;

    public String generateToken(final Long userId) {
        Date issuedAt = new Date(System.currentTimeMillis());
        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .setExpiration(new Date(issuedAt.getTime() + expirationMilliSeconds))
            .setIssuedAt(issuedAt)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public boolean validate(final String token) {
        try {
            return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String parseToken(final String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}