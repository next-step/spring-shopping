package shopping.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.vo.Email;
import shopping.domain.vo.Password;
import shopping.infrastructure.SHA256PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatNoException;

class UserTest {

    @Test
    @DisplayName("사용자는 id, email, 비밀번호를 가지고 있다.")
    void createUser() {
        assertThatNoException()
                .isThrownBy(() -> new User(
                        1L,
                        new Email("test@test.com"),
                        Password.createEncodedPassword("test", new SHA256PasswordEncoder())
                ));
    }
}
