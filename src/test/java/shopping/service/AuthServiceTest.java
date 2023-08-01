package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.domain.User;
import shopping.domain.exception.StatusCodeException;
import shopping.dto.UserJoinRequest;
import shopping.infra.JwtUtils;
import shopping.persist.UserRepository;

@ExtendWith(SpringExtension.class)
@DisplayName("AuthService 클래스")
@ContextConfiguration(classes = {AuthService.class, JwtUtils.class})
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @Nested
    @DisplayName("joinUser 메소드는")
    class joinUser_method {

        @Test
        @DisplayName("email이 중복되면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_duplicated_email() {
            // given
            UserJoinRequest request = new UserJoinRequest("hello@hello.world", "hello!123");
            User user = new User(request.getEmail(), request.getPassword());
            Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

            // when
            Exception exception = catchException(() -> authService.joinUser(request));

            // then
            assertStatusCodeException(exception, "AUTH-SERVICE-401");
        }

        private void assertStatusCodeException(final Exception exception, final String expectedStatus) {
            assertThat(exception.getClass()).isEqualTo(StatusCodeException.class);
            assertThat(((StatusCodeException) exception).getStatus()).isEqualTo(expectedStatus);
        }
    }
}
