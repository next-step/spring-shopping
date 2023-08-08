package shopping.auth.service;

import java.text.MessageFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.app.api.AuthUseCase;
import shopping.auth.app.api.request.LoginRequest;
import shopping.auth.app.api.response.TokenResponse;
import shopping.auth.app.domain.User;
import shopping.auth.app.domain.exception.DoesNotExistUserException;
import shopping.auth.app.spi.UserRepository;
import shopping.auth.service.infra.JwtUtils;

@Service
@Transactional(readOnly = true)
public class AuthService implements AuthUseCase {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthService(final UserRepository userRepository, final JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public TokenResponse authenticate(final LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new DoesNotExistUserException(
                        MessageFormat.format("email\"{0}\"에 해당하는 user를 찾을 수 없습니다.", request.getEmail())));

        user.assertPassword(request.getPassword());

        return new TokenResponse(jwtUtils.generate(user));
    }
}
