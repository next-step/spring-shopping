package shopping.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;

class NameTest {

    @Test
    @DisplayName("이름은 20자 이하이다.")
    void createNameWithLength20() {
        // when, then
        assertThatNoException().isThrownBy(() -> new Name("a".repeat(20)));
    }

    @Test
    @DisplayName("이름은 20자를 넘을 수 없다.")
    void createNameWithLength21() {
        // when, then
        assertThatCode(() -> new Name("a".repeat(21)))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("이름은 20자를 넘을 수 없습니다");
    }
}
