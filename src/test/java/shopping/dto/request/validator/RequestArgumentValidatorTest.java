package shopping.dto.request.validator;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ArgumentValidateFailException;

class RequestArgumentValidatorTest {

    @DisplayName("문자열 검사 통과")
    @ParameterizedTest
    @ValueSource(strings = {"asdf", "helloworld"})
    void validateStringArgumentSuccess(String target) {
        // when, then
        assertThatCode(() -> RequestArgumentValidator.validateStringArgument(target, "target", 12))
                .doesNotThrowAnyException();
    }

    @DisplayName("문자열 검사 실패 예외 발생")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "a123456789b123456789"})
    void validateStringArgumentThrow(String target) {
        // when, then
        assertThatCode(() -> RequestArgumentValidator.validateStringArgument(target, "target", 12))
                .isInstanceOf(ArgumentValidateFailException.class);
    }

    @DisplayName("숫자 검사 성공")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    void validateNumberArgumentSuccess(Long target) {
        // when, then
        assertThatCode(() -> RequestArgumentValidator.validateNumberArgument(target, "target"))
                .doesNotThrowAnyException();
    }

    @DisplayName("숫자 검사 실패 예외 발생")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1L})
    void validateNumberArgumentThrow(Long target) {
        // when, then
        assertThatCode(() -> RequestArgumentValidator.validateNumberArgument(target, "target"))
                .isInstanceOf(ArgumentValidateFailException.class);
    }

}
