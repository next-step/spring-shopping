package shopping.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class UserTest {

    @Test
    @DisplayName("사용자는 id, email, 비밀번호를 가지고 있다.")
    void createUser() {
        assertThatNoException()
                .isThrownBy(() -> new User(1L, "test@test.com", "test"));
    }
}
