package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.PasswordEncoder;
import shopping.auth.TokenProvider;
import shopping.domain.User;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.PasswordNotMatchException;
import shopping.exception.UserNotFoundException;
import shopping.repository.UserRepository;

@Transactional
@SpringBootTest
@DisplayName("인증 서비스 통합 테스트")
class AuthServiceSpringBootTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @DisplayName("로그인 성공시 액세스 토큰 반환")
    @Test
    void loginSuccessThenReturnAccessTokenInLoginResponse() {
        // given
        String mail = "test1234@example.com";
        String password = "1234";
        String digest = passwordEncoder.encode(password);
        userRepository.save(new User(mail, digest));

        LoginRequest loginRequest = new LoginRequest(mail, password);

        // when
        LoginResponse loginResponse = authService.login(loginRequest);
        String accessToken = "Bearer " + loginResponse.getAccessToken();

        // then
        assertThat(tokenProvider.getEmail(accessToken)).isEqualTo(mail);
    }


    @DisplayName("로그인 시 유저가 데이터베이스에 없을 떄 예외 발생")
    @Test
    void loginFailWhenUserNotInDBThenThrow() {
        // given
        String mail = "test1234@example.com";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(mail, password);

        // when & then
        assertThatCode(() -> authService.login(loginRequest))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("로그인 시 비밀번호 틀렸을 시 예외 발생")
    @Test
    void loginFailWhenPasswordMisMatchThenThrow() {
        // given
        String mail = "test1234@example.com";
        String password = "1234";
        String wrongPassword = "wrongPassword1234";
        String digest = passwordEncoder.encode(password);
        userRepository.save(new User(mail, digest));

        LoginRequest loginRequest = new LoginRequest(mail, wrongPassword);

        // when & then
        assertThatCode(() -> authService.login(loginRequest))
                .isInstanceOf(PasswordNotMatchException.class);
    }
}
