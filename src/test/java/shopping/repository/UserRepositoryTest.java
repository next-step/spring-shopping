package shopping.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.entity.User;
import shopping.domain.vo.Email;
import shopping.domain.vo.Password;
import shopping.infrastructure.PasswordEncoder;
import shopping.infrastructure.SHA256PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepositoryTest(final UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new SHA256PasswordEncoder();
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치하면 User를 반환한다.")
    void findByEmailAndPassword() {
        // when
        Email email = new Email("test@gmail.com");
        Password password = Password.from(passwordEncoder.encode("test1234"));
        User user = userRepository.findByEmailAndPassword(email, password).orElseThrow();

        // then
        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치하지 않으면 Optional Empty를 반환한다.")
    void findByEmailAndPasswordUnMatch() {
        // when
        Email email = new Email("tes2@gmail.com");
        Password password = Password.from(passwordEncoder.encode("test1234"));
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);

        // then
        assertThat(user).isEmpty();
    }
}
