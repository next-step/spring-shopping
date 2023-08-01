package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.domain.exception.StatusCodeException;

@DisplayName("User 클래스")
class UserTest {

    @Nested
    @DisplayName("new 생성자는")
    class new_constructor {

        @Test
        @DisplayName("email 정규표현식에 맞지 않으면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_invalid_email() {
            // given
            String invalidEmail = "hello";
            String password = "hello!123";

            // when
            Exception exception = catchException(() -> new User(invalidEmail, password));

            // then
            assertStatusCodeException(exception, "AUTH-402");
        }

        @Test
        @DisplayName("비밀번호가 8자 미만이면 StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_invalid_password() {
            // given
            String invalidEmail = "hello@hello.com";
            String password = "123456789";

            // when
            Exception exception = catchException(() -> new User(invalidEmail, password));

            // then
            assertStatusCodeException(exception, "AUTH-403");
        }

        @Test
        @DisplayName("비밀번호가 8자 미만이면 StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_under_length_password() {
            // given
            String invalidEmail = "hello@hello.com";
            String password = "123";

            // when
            Exception exception = catchException(() -> new User(invalidEmail, password));

            // then
            assertStatusCodeException(exception, "AUTH-403");
        }

        private void assertStatusCodeException(final Exception exception, final String expectedStatus) {
            assertThat(exception.getClass()).isEqualTo(StatusCodeException.class);
            assertThat(((StatusCodeException) exception).getStatus()).isEqualTo(expectedStatus);
        }
    }

}
