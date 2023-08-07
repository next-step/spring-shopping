package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.LoginRequest;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

public class UserTest {

    @Test
    @DisplayName("성공 : 유저의 비밀번호가 일치할 경우")
    void successLogin() {
        // given
        UserEntity userEntity = new UserEntity(1L, "test@test.com", "test_password");
        LoginRequest loginRequest = new LoginRequest("test@test.com", "test_password");

        // when
        User user = User.from(userEntity);

        // then
        assertThatNoException()
            .isThrownBy(() -> user.matchPassword(loginRequest.getPassword()));
    }

    @Test
    @DisplayName("예외 : 유저의 비밀번호가 불일치할 경우")
    void failLogin() {
        // given
        UserEntity userEntity = new UserEntity(1L, "test@test.com", "test_password");
        LoginRequest loginRequest = new LoginRequest("test@test.com", "other_password");

        // when
        User user = User.from(userEntity);

        // then
        assertThatThrownBy(() -> user.matchPassword(loginRequest.getPassword()))
            .isInstanceOf(ShoppingException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.INVALID_PASSWORD);
    }

}
