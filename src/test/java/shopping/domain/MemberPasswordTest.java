package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import shopping.exception.ShoppingException;

class MemberPasswordTest {

    @Test
    @DisplayName("회원 비밀번호을 생성한다.")
    void createMemberPassword() {
        /* given */
        final String password = "woowacamp";

        /* when & then */
        assertThatCode(() -> new MemberPassword(password))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 비밀번호이 null이거나 공백일 때 ShoppingException을 던진다.")
    void createMemberPasswordFailWithNullOrEmptyValue(final String password) {
        /* given */

        /* when & then */
        assertThatCode(() -> new MemberPassword(password))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("회원 비밀번호는 비어있거나 공백이면 안됩니다. 입력값: " + password);
    }

    @Test
    @DisplayName("회원 비밀번호의 길이가 30을 넘으면 ShoppingException을 던진다.")
    void createMemberPasswordFailWithGreaterThan30Value() {
        /* given */
        final String wrongPassword = "a".repeat(31);

        /* when & then */
        assertThatCode(() -> new MemberPassword(wrongPassword))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("회원 비밀번호는 30자 이하여야 합니다. 입력값: " + wrongPassword);
    }

    @Test
    @DisplayName("비밀번호이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final MemberPassword origin = new MemberPassword("woowacamp");
        final MemberPassword another = new MemberPassword("woowacamp");

        /* when & then */
        assertThat(origin).isEqualTo(another);
        assertThat(origin).hasSameHashCodeAs(another);
    }
}
