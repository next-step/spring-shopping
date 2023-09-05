package shopping.domain.member;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 테스트")
class MemberTest {

    @Test
    @DisplayName("회원을 생성한다.")
    void createMember() {
        /* given */
        final MemberEmail email = new MemberEmail("woowacamp@naver.com");
        final MemberPassword password = new MemberPassword("woowacamp");

        /* when & then */
        assertThatCode(() -> new Member(email, password))
            .doesNotThrowAnyException();
    }
}
