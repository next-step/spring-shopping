package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.domain.member.MemberEmail;
import shopping.exception.ShoppingException;

class MemberEmailTest {

    @Test
    @DisplayName("회원 이메일을 생성한다.")
    void createMemberEmail() {
        /* given */
        final String email = "woowacamp@naver.com";

        /* when & then */
        assertThatCode(() -> new MemberEmail(email))
            .doesNotThrowAnyException();
    }


    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 이메일이 null이거나 공백일 때 ShoppingException을 던진다.")
    void createMemberEmailFailWithNullOrEmptyValue(final String email) {
        /* given */

        /* when & then */
        assertThatCode(() -> new MemberEmail(email))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("회원 이메일은 비어있거나 공백이면 안됩니다. 입력값: " + email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"user.email.com", "user@ema il.com", "user.email@com"})
    @DisplayName("회원 이메일이 정규식과 일치하지 않는 경우 ShoppingException을 던진다.")
    void createMemberEmailFailDoesNotMathRegex(final String wrong) {
        /* given */

        /* when & then */
        assertThatCode(() -> new MemberEmail(wrong))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("회원 이메일이 형식에 맞지 않습니다. 입력값: " + wrong);
    }


    @Test
    @DisplayName("이메일이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final MemberEmail origin = new MemberEmail("woowacamp@naver.com");
        final MemberEmail another = new MemberEmail("woowacamp@naver.com");

        /* when & then */
        assertThat(origin).isEqualTo(another);
        assertThat(origin).hasSameHashCodeAs(another);
    }
}
