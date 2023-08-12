package shopping.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordTest {
    @Test
    @DisplayName("비밀번호는 암호화되어 생성된다.")
    void passwordEncrypted() {
        Password password = Password.createEncodedPassword("test1234", rawPassword -> rawPassword + "a");

        assertThat(password.getValue()).isEqualTo("test1234a");
        assertThat(password.getValue().length()).isEqualTo(9);
    }
}
