package shopping.service;

import java.util.Objects;
import org.springframework.stereotype.Service;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;
import shopping.repository.UserRepository;
import shopping.utils.JwtProvider;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserService(final UserRepository userRepository, final JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    public LoginResponse login(final LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new ShoppingException(ErrorCode.INVALID_EMAIL));
        if (!Objects.equals(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new ShoppingException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.generateToken(userEntity.getId());

        return LoginResponse.from(accessToken);
    }
}
