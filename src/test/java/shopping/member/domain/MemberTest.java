package shopping.member.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 단위 테스트")
class MemberTest {

    @Test
    @DisplayName("비밀번호가 일치하면 true를 반환한다.")
    void loginSuccess() {
        String password = "hello";
        Member member = new Member("hello", password);

        assertThat(member.login(password)).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 false를 반환한다.")
    void loginFail() {
        String password = "hello";
        String failPassword = "bye";
        Member member = new Member("hello", password);

        assertThat(member.login(failPassword)).isFalse();
    }
}
