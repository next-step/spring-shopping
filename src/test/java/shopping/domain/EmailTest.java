package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;

class EmailTest {

    @Test
    @DisplayName("이메일 길이는 50자 이하이다.")
    void createEmailWithLength50() {
        // when, then
        assertThatNoException().isThrownBy(() -> new Email("a".repeat(42) + "@abc.com"));
    }

    @Test
    @DisplayName("이메일 길이가 50자 초과이면 오류를 반환한다.")
    void createEmailWithLength51() {
        // when, then
        assertThatCode(() -> new Email("a".repeat(43) + "@abc.com"))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("이메일 길이는 50자를 넘을 수 없습니다.");
    }

    @Test
    @DisplayName("이메일은 올바른 형식을 따른다.")
    void createEmailWithValidForm() {
        // when, then
        assertThatNoException().isThrownBy(() -> new Email("seungyoon@woowa.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcd", "a@b@c", "abcd@abc.a", "ab*cd@gmail.com"})
    @DisplayName("올바르지 않은 형식의 이메일을 생성하면 오류를 반환한다.")
    void createEmailWithInvalidForm(String email) {
        // when, then
        assertThatCode(() -> new Email(email))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }
}
