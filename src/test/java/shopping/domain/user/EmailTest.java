package shopping.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.general.InvalidRequestException;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @DisplayName("이메일 형식을 확인한다.")
    @Test
    void validEmail() {
        assertThatNoException().isThrownBy(() -> new Email("asdf@asdf.asdf"));
    }

    @DisplayName("이메일 형식이 올바르지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"   ", "a@a.a", "aasdf.asdf", "asdfasdf@asdfasd"})
    void invalidEmail(String email) {
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(InvalidRequestException.class);
    }
}
