package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.member.Member;
import shopping.domain.member.MemberEmail;
import shopping.domain.member.MemberPassword;
import shopping.dto.request.LoginRequest;
import shopping.repository.MemberRepository;

@DisplayName("회원 Service 테스트")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("로그인 정보와 일치하는 회원이 있는지 확인할 수 있다.")
    void matchMember() {
        /* given */
        final String email = "woowacamp@naver.com";
        final String password = "woowacamp";
        final Member dummyMember = new Member(new MemberEmail(email), new MemberPassword(password));

        given(memberRepository.findByEmail(new MemberEmail(email)))
            .willReturn(Optional.of(dummyMember));

        /* when */
        final Member result = memberService.matchMember(new LoginRequest(email, password));

        /* then */
        assertThat(result.getEmail()).isEqualTo(email);
    }
}
