package shopping.application;

import org.springframework.stereotype.Service;
import shopping.auth.PasswordEncoder;
import shopping.auth.TokenProvider;
import shopping.domain.Email;
import shopping.domain.User;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.exception.PasswordNotMatchException;
import shopping.exception.UserNotFoundException;
import shopping.repository.UserRepository;

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
                .orElseThrow(() -> new UserNotFoundException(loginRequest.getEmail()));
        if (!passwordEncoder.match(loginRequest.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }
        String accessToken = tokenProvider.issueToken(user);

        return new LoginResponse(accessToken);
    }
}
