package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ArgumentValidateFailException;

@DisplayName("비밀번호 도메인 테스트")
class PasswordTest {

    @DisplayName("비밀 번호 객체 정상 생성")
    @Test
    void createPasswordSuccess() {
        // given
        String passwordString = "1234";

        // when, then
        assertThatCode(() -> new Password(passwordString))
                .doesNotThrowAnyException();
    }

    @DisplayName("입력이 null 일시 생성 실패")
    @Test
    void nullPasswordCreateFail() {
        // given
        String passwordString = null;

        // when, then
        assertThatCode(() -> new Password(passwordString))
                .isInstanceOf(ArgumentValidateFailException.class);
    }
}
