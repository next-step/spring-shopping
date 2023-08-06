package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.Member;
import shopping.dto.LoginRequest;
import shopping.exception.AuthException;
import shopping.jwt.TokenManager;
import shopping.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("AuthService 클래스")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TokenManager tokenProvider;

    @Nested
    @DisplayName("login 메소드는")
    class login_Method {

        @Test
        @DisplayName("유효한 회원일 경우 로그인에 성공한다")
        void LoginSuccess_WhenValidMember() {
            // given
            String email = "woowa1@woowa.com";
            String password = "1234";

            LoginRequest loginRequest = new LoginRequest(email, password);

            Member member = new Member(1L, email, password);

            given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
            given(tokenProvider.createToken(member.getId())).willReturn("token");

            // when
            Exception exception = catchException(() -> authService.login(loginRequest));

            // then
            assertThat(exception).isNull();

            verify(memberRepository).findByEmail(loginRequest.getEmail());
            verify(tokenProvider).createToken(member.getId());
        }

        @Test
        @DisplayName("존재하지 않는 회원일 경우 AuthException 을 던진다")
        void throwAuthException_WhenInvalidMember() {
            // given
            String email = "woo@woowa.com";
            String password = "1234";

            LoginRequest loginRequest = new LoginRequest(email, password);

            given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> authService.login(loginRequest));

            // then
            assertThat(exception).isInstanceOf(AuthException.class);
            assertThat(exception.getMessage()).contains("존재하지 않는 사용자입니다");

            verify(memberRepository).findByEmail(email);
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않을 경우, AuthException 을 던진다")
        void throwAuthException_WhenNotMatchPassword() {
            // given
            String email = "woowa1@woowa.com";
            String password = "12";

            LoginRequest loginRequest = new LoginRequest(email, password);

            Member member = new Member(email, "1234");

            given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

            // when
            Exception exception = catchException(() -> authService.login(loginRequest));

            // then
            assertThat(exception).isInstanceOf(AuthException.class);
            assertThat(exception.getMessage()).contains("비밀번호가 일치하지 않습니다");

            verify(memberRepository).findByEmail(email);
        }
    }

}
