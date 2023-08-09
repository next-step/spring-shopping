package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.member.Member;
import shopping.domain.member.MemberEmail;

@DisplayName("회원 Repository 테스트")
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("MemberEmail로 회원을 찾을 수 있다.")
    void findByEmail() {
        /* given */
        final MemberEmail email = new MemberEmail("woowacamp@naver.com");
        final MemberEmail wrong = new MemberEmail("pppppp@qqqqq.com");

        /* when */
        final Optional<Member> exist = memberRepository.findByEmail(email);
        final Optional<Member> notExist = memberRepository.findByEmail(wrong);

        /* then */
        assertThat(exist).isPresent();
        assertThat(notExist).isNotPresent();
    }
}
