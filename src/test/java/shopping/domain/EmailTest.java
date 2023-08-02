package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;

class EmailTest {

    @Test
    @DisplayName("이메일 길이는 50자 이하이다.")
    void createEmailWithLength50() {
        // when, then
        assertThatNoException().isThrownBy(() -> new Email("a".repeat(50)));
    }

    @Test
    @DisplayName("이메일 길이가 50자 초과이면 오류를 반환한다.")
    void createEmailWithLength51() {
        // when, then
        assertThatCode(() -> new Email("a".repeat(51)))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("이메일 길이는 50자를 넘을 수 없습니다.");
    }
}