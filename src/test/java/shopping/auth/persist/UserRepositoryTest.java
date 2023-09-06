package shopping.auth.persist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.auth.domain.User;

@DisplayName("UserRepository 클래스")
@ContextConfiguration(classes = {UserRepository.class})
class UserRepositoryTest extends JpaTest {

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("findByEmail 메소드는")
    class findByEmail_method {

        @Test
        @DisplayName("email이 일치하는 User를 반환한다.")
        void return_email_matched_user() {
            // given
            String email = "hello@hello.world";
            User user = new User(email, "hello!123");
            userRepository.saveUser(user);

            // when
            Optional<User> result = userRepository.findByEmail(email);

            // then
            assertUser(result, email);
        }

        @Test
        @DisplayName("email이 일치하는 User가 없다면, Optional.empty를 반환한다.")
        void return_empty_optional_when_no_user() {
            // when
            Optional<User> result = userRepository.findByEmail("empty");

            // then
            assertThat(result).isNotPresent();
        }

        private void assertUser(final Optional<User> result, final String email) {
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isNotNull();
            assertThat(result.get().getEmail()).isEqualTo(email);
        }
    }
}
