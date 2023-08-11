package shopping.service;

import org.springframework.stereotype.Service;
import shopping.auth.JwtProvider;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    public AuthService(final JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public String createToken(final Long memberId) {
        return jwtProvider.createToken(memberId);
    }
}
