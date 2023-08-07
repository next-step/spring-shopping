package shopping.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThatCode;

class LoginRequestTest {

    @Test
    @DisplayName("email이 null이면 오류를 반환한다.")
    void loginWithNullEmail() {
        assertThatCode(() -> new LoginRequest(null, "abc"))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("email은 필수 항목입니다");
    }

    @Test
    @DisplayName("password가 null이면 오류를 반환한다.")
    void loginWithNullPassword() {
        assertThatCode(() -> new LoginRequest("asf", null))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("password는 필수 항목입니다");
    }
}
