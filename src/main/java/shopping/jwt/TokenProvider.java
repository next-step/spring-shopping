package shopping.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {

    @Value("${shopping.token.secretKey}")
    private static String SECRET_KEY;
    private static final long TOKEN_VALIDITY_MILLI = 60 * 60 * 1000;

    public String createToken(Long id) {
        Date date = new Date();

        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(id))
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TOKEN_VALIDITY_MILLI));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
