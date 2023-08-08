package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.when;
import static shopping.exception.ShoppingErrorType.PASSWORD_NOT_MATCH;
import static shopping.fixture.MemberFixture.EMAIL;
import static shopping.fixture.MemberFixture.MEMBER_ID;
import static shopping.fixture.MemberFixture.PASSWORD;
import static shopping.fixture.MemberFixture.createMember;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.auth.JwtTokenProvider;
import shopping.domain.member.Member;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.ShoppingException;
import shopping.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LoginService.class)
class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        final Member member = createMember(MEMBER_ID);
        final String validAccessToken = "VALID_ACCESS_TOKEN";
        when(memberRepository.findByEmail(EMAIL.getValue())).thenReturn(Optional.of(member));
        when(jwtTokenProvider.createToken(MEMBER_ID)).thenReturn(validAccessToken);

        final LoginResponse loginResponse = loginService.login(new LoginRequest(EMAIL.getValue(), PASSWORD.getValue()));

        assertThat(loginResponse.getToken()).isEqualTo(validAccessToken);
    }

    @Test
    @DisplayName("로그인 시 등록되지 않은 이메일로 로그인하는 경우 예외를 던진다.")
    void loginWithInvalidEmail() {
        when(memberRepository.findByEmail("INVALID")).thenReturn(Optional.empty());

        Exception exception = catchException(
                () -> loginService.login(new LoginRequest(EMAIL.getValue(), PASSWORD.getValue())));

        assertThat(exception).isInstanceOf(ShoppingException.class);
    }
    
    @Test
    @DisplayName("로그인 시 잘못된 비밀번호로 로그인하는 경우 예외를 던진다.")
    void loginWithInvalidPassword() {
        final Member member = createMember(MEMBER_ID);
        when(memberRepository.findByEmail(EMAIL.getValue())).thenReturn(Optional.of(member));

        Exception exception = catchException(
                () -> loginService.login(new LoginRequest(EMAIL.getValue(), "123asd!@#")));

        assertThat(exception).isInstanceOf(ShoppingException.class);
        assertThat(exception.getMessage()).isEqualTo(PASSWORD_NOT_MATCH.getMessage());
    }
}
