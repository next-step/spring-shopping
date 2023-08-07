package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.exception.MemberException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@DisplayName("Member 클래스")
public class MemberTest {

    @Nested
    @DisplayName("new 생성자는")
    class Member_Constructor {

        @Test
        @DisplayName("Member 를 생성한다.")
        void createMember() {
            // given
            String email = "mara@naver.com";
            String password = "password";

            // when
            Exception exception = catchException(() -> new Member(email, password));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("Member email 이 null 이면 MemberException 을 던진다.")
        void throwMemberException_whenEmailIsNull() {
            // given
            String email = null;
            String password = "password";

            // when
            Exception exception = catchException(() -> new Member(email, password));

            // then
            assertThat(exception).isInstanceOf(MemberException.class);
            assertThat(exception.getMessage()).contains("사용자 이메일이 존재하지 않습니다");
        }

        @Test
        @DisplayName("Member email 형식이 유효하지 않다면 MemberException 을 던진다.")
        void throwMemberException_whenEmailIsNotValid() {
            // given
            String email = "mara@@@@";
            String password = "password";

            // when
            Exception exception = catchException(() -> new Member(email, password));

            // then
            assertThat(exception).isInstanceOf(MemberException.class);
            assertThat(exception.getMessage()).contains("올바른 이메일 형식이 아닙니다");
        }

        @Test
        @DisplayName("Member password 가 null 이면 MemberException 을 던진다.")
        void throwMemberException_whenPasswordIsNull() {
            // given
            String email = "mara@naver.com";
            String password = null;

            // when
            Exception exception = catchException(() -> new Member(email, password));

            // then
            assertThat(exception).isInstanceOf(MemberException.class);
            assertThat(exception.getMessage()).contains("사용자 비밀번호가 존재하지 않습니다");
        }

        @Test
        @DisplayName("Member password 가 공백이면 MemberException 을 던진다.")
        void throwMemberException_whenPasswordIsEmpty() {
            // given
            String email = "mara@naver.com";
            String password = "";

            // when
            Exception exception = catchException(() -> new Member(email, password));

            // then
            assertThat(exception).isInstanceOf(MemberException.class);
            assertThat(exception.getMessage()).contains("사용자 비밀번호가 존재하지 않습니다");
        }
    }
}
