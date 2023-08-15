package shopping.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.domain.SecurityInfo;
import shopping.domain.SecurityInfoManager;
import shopping.domain.UserInfo;
import shopping.exception.TokenException;

@Component
public class JwtSecurityInfoManager implements SecurityInfoManager {

    private static final long TOKEN_VALIDITY_MILLI = 60 * 60 * 1000;
    public static final String MEMBER_ID_KEY = "memberId";

    private final String secretKey;
    private final ObjectMapper objectMapper;

    public JwtSecurityInfoManager(@Value("${shopping.token.secretKey}") String secretKey, ObjectMapper objectMapper) {
        this.secretKey = secretKey;
        this.objectMapper = objectMapper;
    }

    public String encode(SecurityInfo securityInfo) {
        Date date = new Date();

        try {
            String jwt = objectMapper.writeValueAsString(securityInfo);

            return Jwts.builder()
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TOKEN_VALIDITY_MILLI))
                .claim(MEMBER_ID_KEY, jwt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        } catch (JsonProcessingException e) {
            throw new TokenException("토큰 형식이 유효하지 않습니다.");
        }
    }

    public SecurityInfo decode(String token) {
        try {
            final String json = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get(MEMBER_ID_KEY, String.class);
            return objectMapper.readValue(json, UserInfo.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenException("토큰이 유효하지 않습니다");
        } catch (JsonProcessingException e) {
            throw new TokenException("토큰 형식이 유효하지 않습니다.");
        }
    }
}
