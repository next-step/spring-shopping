package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.domain.CartProduct;
import shopping.domain.Email;
import shopping.domain.Member;

@DisplayName("MemberRepository 클래스")
@DataJpaTest
@Import(value = {MemberRepository.class})
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("findByEmail 메소드는")
    class FindByEmail {

        @Test
        @DisplayName("email에 해당하는 Member 를 반환한다.")
        void returnMemberByEmail() {
            // given
            Email email = new Email("woowa1@woowa.com");

            // when
            Optional<Member> result = memberRepository.findByEmail(email);

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getEmail()).isEqualTo(email);
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class FindById {

        @Test
        @DisplayName("id 해당하는 Member 를 반환한다.")
        void returnMemberById() {
            // given
            Long memberId = 1L;

            // when
            Optional<Member> result = memberRepository.findById(memberId);

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(memberId);
        }
    }
}