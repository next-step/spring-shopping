package shopping.member.service;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.auth.TokenProvider;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.member.dto.LoginRequest;
import shopping.member.dto.LoginResponse;
import shopping.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Spy
    private TokenProvider tokenProvider = new TokenProvider("woowatechcamp", 10000);
    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("로그인이 정상적으로 성공한다")
    void loginSuccess() {
        LoginRequest loginRequest = new LoginRequest("woowa", "woowa");
        Mockito.when(memberRepository.findByEmail("woowa"))
            .thenReturn(Optional.of(new Member(1L, "woowa", "woowa")));

        LoginResponse loginResponse = memberService.loginMember(loginRequest);

        Assertions.assertThat(loginResponse.getAccessToken()).isNotNull();
        Assertions.assertThat(loginResponse.getAccessToken().split("\\.")).hasSize(3);
    }

    @Test
    @DisplayName("로그인 유저 정보가 존재하지 않은 경우 예외를 던진다.")
    void throwExceptionIfUserDoesNotExist() {
        LoginRequest loginRequest = new LoginRequest("woowa", "woowa");
        Mockito.when(memberRepository.findByEmail("woowa"))
            .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> memberService.loginMember(loginRequest))
            .isInstanceOf(WooWaException.class)
            .hasMessage("로그인 정보가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("로그인 비밀번호가 틀린 경우 예외를 던진다.")
    void throwExceptionIfPasswordNotEqual() {
        LoginRequest loginRequest = new LoginRequest("woowa", "woowa");
        Mockito.when(memberRepository.findByEmail("woowa"))
            .thenReturn(Optional.of(new Member(1L, "woowa", "woowa1")));

        Assertions.assertThatThrownBy(() -> memberService.loginMember(loginRequest))
            .isInstanceOf(WooWaException.class)
            .hasMessage("로그인 정보가 일치하지 않습니다.");
    }
}
