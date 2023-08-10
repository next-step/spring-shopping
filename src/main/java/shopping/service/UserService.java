package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;
import shopping.repository.UserRepository;
import shopping.utils.JwtProvider;

@Service
@Transactional(readOnly = true)
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
        userEntity.matchPassword(loginRequest.getPassword());

        final String accessToken = jwtProvider.generateToken(String.valueOf(userEntity.getId()));
        return LoginResponse.from(accessToken);
    }

}
