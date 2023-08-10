package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.PBKDF2PasswordEncoder;
import shopping.auth.PasswordEncoder;
import shopping.auth.TokenProvider;
import shopping.domain.user.Email;
import shopping.domain.user.User;
import shopping.dto.web.request.LoginRequest;
import shopping.dto.web.response.LoginResponse;
import shopping.exception.auth.UserNotFoundException;
import shopping.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder = new PBKDF2PasswordEncoder();

    public AuthService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(new Email(loginRequest.getEmail()))
                .orElseThrow(UserNotFoundException::new);
        user.matchPassword(loginRequest.getPassword(), passwordEncoder);
        String accessToken = tokenProvider.issueToken(user);
        return new LoginResponse(accessToken);
    }
}
