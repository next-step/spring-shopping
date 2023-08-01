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
import shopping.domain.Member;
import shopping.domain.MemberEmail;
import shopping.domain.MemberPassword;
import shopping.dto.request.LoginRequest;
import shopping.repository.MemberRepository;

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
        final MemberEmail email = new MemberEmail("woowacamp@naver.com");
        final MemberPassword password = new MemberPassword("woowacamp");
        final Member dummyMember = new Member(email, password);

        given(memberRepository.findByEmail(email)).willReturn(Optional.of(dummyMember));

        /* when */
        final Member result = memberService.matchMember(
            new LoginRequest(email.getValue(), password.getValue())
        );

        /* then */
        assertThat(result.getEmail()).isEqualTo(email);
    }
}
