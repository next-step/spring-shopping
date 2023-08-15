package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.entity.User;
import shopping.domain.vo.Password;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.BadLoginRequestException;
import shopping.infrastructure.PasswordEncoder;
import shopping.infrastructure.TokenProvider;
import shopping.repository.UserRepository;

@Service
@Transactional
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final TokenProvider tokenProvider,
                       final UserRepository userRepository,
                       final PasswordEncoder passwordEncoder) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(final LoginRequest request) {
        final Password password = request.getPassword();
        password.encode(passwordEncoder);

        final User user = userRepository.findByEmailAndPassword(request.getEmail(), password)
                .orElseThrow(BadLoginRequestException::new);

        return new LoginResponse(tokenProvider.create(user.getId().toString()));
    }
}
