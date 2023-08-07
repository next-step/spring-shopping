package shopping.auth.service.infra;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.auth.domain.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JwtUtils.class})
@DisplayName("JwtUtils 클래스는")
class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Nested
    @DisplayName("generate 메소드는")
    class generate_method {

        @Test
        @DisplayName("User를 받아서, jwtToken을 생성한다.")
        void create_token_when_receive_user() {
            // given
            User user = new User(1L, "hello@hello.world", "hello!123");

            // when
            String accessToken = jwtUtils.generate(user);

            // then
            assertThat(Long.valueOf(jwtUtils.payload("bearer " + accessToken))).isEqualTo(user.getId());
        }
    }
}
