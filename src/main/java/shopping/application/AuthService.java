package shopping.application;

import org.springframework.stereotype.Service;
import shopping.domain.user.Email;
import shopping.domain.user.Password;
import shopping.domain.user.User;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;
import shopping.infrastructure.JwtProvider;
import shopping.infrastructure.SHA256PasswordEncoder;
import shopping.repository.UserRepository;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final SHA256PasswordEncoder encoder;
    private final UserRepository userRepository;

    public AuthService(JwtProvider jwtProvider, SHA256PasswordEncoder encoder, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        Email email = new Email(request.getEmail());
        Password password = Password.createEncodedPassword(request.getPassword(), encoder);
        User user = userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ShoppingException(ErrorType.WRONG_LOGIN_REQUEST));

        return new LoginResponse(jwtProvider.create(user.getId().toString()));
    }
}
