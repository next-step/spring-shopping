package shopping.auth.service;

import java.text.MessageFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.domain.User;
import shopping.auth.domain.status.UserExceptionStatus;
import shopping.auth.dto.LoginRequest;
import shopping.auth.dto.TokenResponse;
import shopping.auth.dto.UserJoinRequest;
import shopping.auth.infra.JwtUtils;
import shopping.auth.persist.UserRepository;
import shopping.core.exception.BadRequestException;

@Service
public class AuthService {

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
                    throw new BadRequestException(MessageFormat.format("이미 가입된 user\"{0}\" 입니다.", request),
                            UserExceptionStatus.ALREADY_EXIST_USER.getStatus());
                });
    }

    @Transactional(readOnly = true)
    public TokenResponse authenticate(final LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(
                        MessageFormat.format("email\"{0}\"에 해당하는 user를 찾을 수 없습니다.", request.getEmail()),
                        UserExceptionStatus.NOT_EXIST_USER.getStatus()));

        user.assertPassword(request.getPassword());

        return new TokenResponse(jwtUtils.generate(user));
    }
}
