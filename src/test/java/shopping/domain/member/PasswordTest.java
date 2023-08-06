package shopping.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"abc$131", "abc$131!&*asdBBB1"})
    @DisplayName("패스워드를 생성할 수 있다.")
    void createPassword(final String value) {
        final Password password = Password.from(value);

        assertThat(password.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"abc$13", "abcdefghqwe", "1234567", "abcdefg!@#123123544"})
    @DisplayName("소문자, 특수문자 7글자 이상 18글자 이하가 아닌 입력으로 패스워드를 생성할 때 예외를 던진다.")
    void validatePassword(final String value) {
        assertThatThrownBy(() -> Password.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("패스워드는 소문자, 특수문자 7글자 이상 18글자 이하여야 합니다.");
    }

    @Test
    @DisplayName("입력된 비밀번호와 일치하는지 여부를 확인한다.")
    void match() {
        final String samePassword = "abc$!13";
        final String notSamePassword = "abc1$!13";
        final Password password = Password.from(samePassword);

        assertThat(password.isMatch(Password.from(samePassword))).isTrue();
        assertThat(password.isMatch(Password.from(notSamePassword))).isFalse();
    }
}
