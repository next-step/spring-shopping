package shopping.application;

import org.springframework.stereotype.Service;
import shopping.domain.User;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.exception.ShoppingException;
import shopping.infrastructure.JwtProvider;
import shopping.repository.UserRepository;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public AuthService(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new ShoppingException("잘못된 로그인 요청입니다."));

        return new LoginResponse(jwtProvider.create(user.getId().toString()));
    }
}
