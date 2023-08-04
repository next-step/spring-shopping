package shopping.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @DisplayName("정상 로그인 요청시 액세스 토큰을 반환한다.")
    @Test
    void login() {
        // given
        String email = "test@example.com";
        String password = "1234";
        String digest = passwordEncoder.encode(password);

        User user = new User(email, digest);
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest(email, password);

        // when
        LoginResponse loginResponse = authService.login(loginRequest);

        // then
        assertThat(tokenProvider.getEmail(loginResponse.getAccessToken())).isEqualTo(email);
    }

    @DisplayName("로그인 시 유저가 데이터베이스에 없을 떄 예외 발생")
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

    @DisplayName("로그인 시 비밀번호 틀렸을 시 예외 발생")
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