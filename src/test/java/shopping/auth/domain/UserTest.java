package shopping.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.core.exception.BadRequestException;

@DisplayName("User 클래스")
class UserTest {

    private void assertBadRequestException(final Exception exception, final String expectedStatus) {
        assertThat(exception.getClass()).isEqualTo(BadRequestException.class);
        assertThat(((BadRequestException) exception).getStatus()).isEqualTo(expectedStatus);
    }

    @Nested
    @DisplayName("new 생성자는")
    class new_constructor {

        @Test
        @DisplayName("email 정규표현식에 맞지 않으면, BadRequestException을 던진다.")
        void throw_BadRequestException_when_invalid_email() {
            // given
            String invalidEmail = "hello";
            String password = "hello!123";

            // when
            Exception exception = catchException(() -> new User(invalidEmail, password));

            // then
            assertBadRequestException(exception, "AUTH-402");
        }

        @Test
        @DisplayName("비밀번호가 8자 미만이면 BadRequestException을 던진다.")
        void throw_BadRequestException_when_invalid_password() {
            // given
            String invalidEmail = "hello@hello.com";
            String password = "123456789";

            // when
            Exception exception = catchException(() -> new User(invalidEmail, password));

            // then
            assertBadRequestException(exception, "AUTH-403");
        }

        @Test
        @DisplayName("비밀번호가 8자 미만이면 BadRequestException을 던진다.")
        void throw_BadRequestException_when_under_length_password() {
            // given
            String invalidEmail = "hello@hello.com";
            String password = "123";

            // when
            Exception exception = catchException(() -> new User(invalidEmail, password));

            // then
            assertBadRequestException(exception, "AUTH-403");
        }
    }

    @Nested
    @DisplayName("assertPassword 메소드는")
    class assertPassword_method {

        @Test
        @DisplayName("일치하는 비밀번호가 들어오면 예외를 던지지 않는다.")
        void does_not_throw_exception_when_password_matched() {
            // given
            String email = "hello@hello.world";
            String password = "hello!123";

            User user = new User(email, password);

            // when
            Exception exception = catchException(() -> user.assertPassword(password));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("일치하는 비밀번호가 들어오면 예외를 던진다.")
        void throw_BadRequestException_when_password_not_matched() {
            // given
            String email = "hello@hello.world";
            String password = "hello!123";
            String invalidPassword = "bye!123";

            User user = new User(email, password);

            // when
            Exception exception = catchException(() -> user.assertPassword(invalidPassword));

            // then
            assertBadRequestException(exception, "AUTH-401");
        }
    }
}
