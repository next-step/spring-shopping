package shopping.application;

import org.springframework.stereotype.Service;
import shopping.auth.PasswordUtil;
import shopping.auth.TokenProvider;
import shopping.domain.User;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(); // TODO CustomException
        if (!PasswordUtil.match(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException();
        }
        String accessToken = TokenProvider.makeToken(user);

        return new LoginResponse(accessToken);
    }
}
