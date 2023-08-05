package shopping.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.ShoppingException;

@DisplayName("Password 단위 테스트")
class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"asd123", "Q1w2", "0032pi9!!0"})
    @DisplayName("올바른 비밀번호 형식(영어 소문자, 숫자를 포함한 4~10자 문자열)으로 생성할 수 있다.")
    void createSuccess(final String value) {
        /* given */

        /* when & then */
        assertThatNoException().isThrownBy(() -> new Password(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"as1", "12345", "qwerty", "0032pi90000"})
    @DisplayName("올바른 비밀번호 형식이 아닌 경우 생성할 수 없다.")
    void createFailure(final String value) {
        /* given */

        /* when & then */
        final ShoppingException exception = assertThrows(ShoppingException.class,
            () -> new Password(value));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD);
    }
}
