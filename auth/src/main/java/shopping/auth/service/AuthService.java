package shopping.auth.service;

import java.text.MessageFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.domain.User;
import shopping.auth.dto.LoginRequest;
import shopping.auth.dto.TokenResponse;
import shopping.auth.dto.UserJoinRequest;
import shopping.auth.infra.JwtUtils;
import shopping.auth.persist.UserRepository;
import shopping.core.exception.StatusCodeException;

@Service
public class AuthService {

    private static final String ALREADY_EXIST_USER = "AUTH-SERVICE-401";
    private static final String NOT_EXIST_USER = "AUTH-SERVICE-402";

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthService(final UserRepository userRepository, final JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public void joinUser(final UserJoinRequest request) {
        throwIfExistUser(request);

        User user = new User(request.getEmail(), request.getPassword());

        userRepository.saveUser(user);
    }

    private void throwIfExistUser(final UserJoinRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(product -> {
                    throw new StatusCodeException(MessageFormat.format("이미 가입된 user\"{0}\" 입니다.", request),
                            ALREADY_EXIST_USER);
                });
    }

    @Transactional(readOnly = true)
    public TokenResponse authenticate(final LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new StatusCodeException(
                        MessageFormat.format("email\"{0}\"에 해당하는 user를 찾을 수 없습니다.", request.getEmail()),
                        NOT_EXIST_USER));

        user.assertPassword(request.getPassword());

        return new TokenResponse(jwtUtils.generate(user));
    }
}
