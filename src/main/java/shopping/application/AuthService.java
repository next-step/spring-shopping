package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.PasswordEncoder;
import shopping.auth.TokenProvider;
import shopping.domain.user.Email;
import shopping.domain.user.User;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.PasswordNotMatchException;
import shopping.exception.UserNotFoundException;
import shopping.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, TokenProvider tokenProvider,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(new Email(loginRequest.getEmail()))
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.match(loginRequest.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }
        String accessToken = tokenProvider.issueToken(user);

        return new LoginResponse(accessToken);
    }
}
