package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shopping.auth.PasswordEncoder;
import shopping.auth.TokenProvider;
import shopping.domain.user.User;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.PasswordNotMatchException;
import shopping.exception.UserNotFoundException;
import shopping.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("인증 서비스 통합 테스트")
class AuthServiceTest extends ServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("정상 로그인 요청시 액세스 토큰 반환")
    @Test
    void login() {
        // given
        String email = "test@example.com";
        String password = "1234";
        String digest = passwordEncoder.encode(password);

        User user = new User(email, digest);
        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        LoginRequest loginRequest = new LoginRequest(email, password);

        // when
        LoginResponse loginResponse = authService.login(loginRequest);

        // then
        assertThat(tokenProvider.getId(loginResponse.getAccessToken())).isEqualTo(userId);
    }

    @DisplayName("존재하지 않는 유저로 로그인하면 예외 발생")
    @Test
    void loginFailWhenUserNotInDB() {
        // given
        String email = "test@example.com";
        String password = "1234";

        LoginRequest loginRequest = new LoginRequest(email, password);

        // when & then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("비밀번호가 틀리면 예외 발생")
    @Test
    void loginFailWhenPasswordMisMatch() {
        // given
        String email = "test@example.com";
        String password = "1234";
        String digest = passwordEncoder.encode(password);

        User user = new User(email, digest);
        userRepository.save(user);

        String wrongPassword = "wrong password";
        LoginRequest loginRequest = new LoginRequest(email, wrongPassword);

        // when & then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(PasswordNotMatchException.class);
    }
}