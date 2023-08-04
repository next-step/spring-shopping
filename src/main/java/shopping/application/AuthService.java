package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.user.Email;
import shopping.domain.user.Password;
import shopping.domain.user.User;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;
import shopping.infrastructure.PasswordEncoder;
import shopping.infrastructure.TokenProvider;
import shopping.repository.UserRepository;

@Service
@Transactional
public class AuthService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public AuthService(final TokenProvider tokenProvider, final PasswordEncoder encoder, final UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(final LoginRequest request) {
        final Email email = new Email(request.getEmail());
        final Password password = Password.createEncodedPassword(request.getPassword(), encoder);
        final User user = userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ShoppingException(ErrorType.WRONG_LOGIN_REQUEST));

        return new LoginResponse(tokenProvider.create(user.getId().toString()));
    }
}
