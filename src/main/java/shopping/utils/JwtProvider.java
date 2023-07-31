package shopping.utils;

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

    public String generateToken(final String email) {
        Date issuedAt = new Date(System.currentTimeMillis());
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(new Date(issuedAt.getTime() + expirationMilliSeconds))
            .setIssuedAt(issuedAt)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }
}
