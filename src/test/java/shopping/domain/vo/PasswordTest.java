package shopping.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class PasswordTest {

    @Test
    @DisplayName("비밀번호가 생성된다.")
    void createPassword() {
        assertThatNoException().isThrownBy(() -> Password.from("password"));
    }

    @Test
    @DisplayName("")
    void encodePassword() {
        // given
        final Password password = Password.from("password");

        // when
        password.encode(password1 -> "encodedPassword");

        // then
        assertThat(password).isEqualTo(Password.from("encodedPassword"));
    }
}
