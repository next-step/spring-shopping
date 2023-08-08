package shopping.auth.service;

import org.springframework.stereotype.Service;
import shopping.auth.util.JwtHelper;

@Service
public class AuthService {

    private final JwtHelper jwtHelper;

    public AuthService(final JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    public String createToken(final Long memberId) {
        return jwtHelper.createToken(memberId);
    }
}
