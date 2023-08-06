package shopping.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.infrastructure.SHA256PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordTest {
    @Test
    @DisplayName("비밀번호는 암호화되어 생성된다.")
    void passwordEncrypted() {
        Password password = Password.createEncodedPassword("test1234", rawPassword -> rawPassword + "a");

        assertThat(password.getPassword()).isEqualTo("test1234a");
        assertThat(password.getPassword().length()).isEqualTo(9);
    }

    @DataJpaTest
    static
    class UserRepositoryTest {

        @Autowired
        UserRepository userRepository;

        @Test
        @DisplayName("아이디와 비밀번호가 일치하면 User를 반환한다.")
        void findByEmailAndPassword() {
            // when
            Email email = new Email("test@gmail.com");
            Password password = Password.createEncodedPassword("test1234", new SHA256PasswordEncoder());
            Optional<User> user = userRepository.findByEmailAndPassword(email, password);

            // then
            assertThat(user.get().getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("아이디와 비밀번호가 일치하지 않으면 Optional Empty를 반환한다.")
        void findByEmailAndPasswordUnMatch() {
            // when
            Email email = new Email("tes2@gmail.com");
            Password password = Password.createEncodedPassword("test1234", new SHA256PasswordEncoder());
            Optional<User> user = userRepository.findByEmailAndPassword(email, password);

            // then
            assertThat(user.isEmpty()).isTrue();
        }
    }
}