package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ArgumentValidateFailException;

@DisplayName("이메일 도메인 테스트")
class EmailTest {

    @DisplayName("검증 성공시 객체 생성")
    @Test
    void emailCreateSuccess() {
        // given
        String email = "test@example.com";

        // when, then
        assertThatCode(() -> new Email(email)).doesNotThrowAnyException();
    }

    @DisplayName("이메일이 null일시 예외 발생")
    @Test
    void emailNullThenThrow() {
        // given
        String email = null;

        // when, then
        assertThatCode(() -> new Email(email))
                .isInstanceOf(ArgumentValidateFailException.class);
    }

    @DisplayName("이메일 형식에 맞지 않을시 예외 발생")
    @Test
    void emailPatternNotMatchThenThrow() {
        // given
        String email = "testexample.com";

        // when, then
        assertThatCode(() -> new Email(email))
                .isInstanceOf(ArgumentValidateFailException.class);
    }
}
