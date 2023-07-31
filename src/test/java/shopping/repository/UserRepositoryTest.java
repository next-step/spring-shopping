package shopping.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("아이디와 비밀번호가 일치하면 User를 반환한다.")
    void findByEmailAndPassword() {
        // when
        Optional<User> user = userRepository.findByEmailAndPassword("test@gmail.com", "test1234");

        // then
        assertThat(user.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치하지 않으면 Optional Empty를 반환한다.")
    void findByEmailAndPasswordUnMatch() {
        // when
        Optional<User> user = userRepository.findByEmailAndPassword("test22@gmail.com", "test1234");

        // then
        assertThat(user.isEmpty()).isTrue();
    }
}