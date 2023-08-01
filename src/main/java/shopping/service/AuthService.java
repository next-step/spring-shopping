package shopping.service;

import java.text.MessageFormat;
import org.springframework.stereotype.Service;
import shopping.domain.User;
import shopping.domain.exception.StatusCodeException;
import shopping.dto.UserJoinRequest;
import shopping.infra.JwtUtils;
import shopping.persist.UserRepository;

@Service
public class AuthService {

    private static final String ALREADY_EXIST_USER = "AUTH-SERVICE-401";

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthService(final UserRepository userRepository, final JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

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

}
