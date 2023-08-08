package shopping.member.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 단위 테스트")
class MemberTest {

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 false를 반환한다.")
    void login() {
        Member member = new Member("hello", "hello");

        Assertions.assertAll(
            () -> assertThat(member.login("hello")).isTrue(),
            () -> assertThat(member.login("bye")).isFalse()
        );
    }
}
