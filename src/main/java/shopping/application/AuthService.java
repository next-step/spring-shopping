package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.entity.User;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.exception.BadLoginRequestException;
import shopping.infrastructure.TokenProvider;
import shopping.repository.UserRepository;

@Service
@Transactional
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public AuthService(final TokenProvider tokenProvider,
                       final UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(final LoginRequest request) {
        final User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(BadLoginRequestException::new);

        return new LoginResponse(tokenProvider.create(user.getId().toString()));
    }
}
