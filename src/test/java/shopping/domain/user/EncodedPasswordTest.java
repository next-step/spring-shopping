package shopping.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.auth.PBKDF2PasswordEncoder;
import shopping.auth.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class EncodedPasswordTest {

    @DisplayName("입력한 비밀번호가 실제 인코딩된 비밀번호와 같은지 확인")
    @Test
    void checkPasswordSame() {
        // given
        String password = "1234";
        PasswordEncoder encoder = new PBKDF2PasswordEncoder();
        EncodedPassword encodedPassword = new EncodedPassword(password, encoder);

        // when, then
        assertThat(encodedPassword.match(password, encoder)).isTrue();
    }
}
