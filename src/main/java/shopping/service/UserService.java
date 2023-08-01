package shopping.service;

import java.util.Objects;
import org.springframework.stereotype.Service;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
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
            .orElseThrow(() -> new IllegalArgumentException("등록된 이메일이 아닙니다"));
        if (!Objects.equals(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다");
        }

        String accessToken = jwtProvider.generateToken(userEntity.getId());

        return LoginResponse.of(accessToken);
    }
}
