package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.Email;
import shopping.domain.Member;
import shopping.domain.SecurityInfoManager;
import shopping.domain.MemberInfo;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.AuthException;
import shopping.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.BDDMockito.given;

@DisplayName("AuthService 클래스")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SecurityInfoManager securityInfoManager;

    @Nested
    @DisplayName("login 메소드는")
    class login_Method {

        @Test
        @DisplayName("유효한 회원일 경우, 토큰을 담은 LoginResponse 를 반환한다")
        void returnLoginResponse_WhenValidMember() {
            // given
            Email email = new Email("woowa1@woowa.com");
            String password = "1234";

            LoginRequest loginRequest = new LoginRequest(email.getValue(), password);

            Member member = new Member(1L, email.getValue(), password);

            given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
            given(securityInfoManager.encode(new MemberInfo(member.getId()))).willReturn("token");

            // when
            LoginResponse loginResponse = authService.login(loginRequest);

            // then
            assertThat(loginResponse.getAccessToken()).isNotBlank();
        }

        @Test
        @DisplayName("존재하지 않는 회원일 경우 AuthException을 던진다")
        void throwAuthException_WhenInvalidMember() {
            // given
            Email email = new Email("woo@woowa.com");
            String password = "1234";

            LoginRequest loginRequest = new LoginRequest(email.getValue(), password);

            given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> authService.login(loginRequest));

            // then
            assertThat(exception).isInstanceOf(AuthException.class);
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않을 경우, AuthException을 던진다")
        void throwAuthException_WhenNotMatchPassword() {
            // given
            Email email = new Email("woowa1@woowa.com");
            String password = "12";

            LoginRequest loginRequest = new LoginRequest(email.getValue(), password);

            Member member = new Member(email.getValue(), "1234");

            given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

            // when
            Exception exception = catchException(() -> authService.login(loginRequest));

            // then
            assertThat(exception).isInstanceOf(AuthException.class);
        }
    }

}
