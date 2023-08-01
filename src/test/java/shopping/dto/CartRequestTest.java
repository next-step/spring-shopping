package shopping.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThatCode;

class CartRequestTest {
    @Test
    @DisplayName("productId가 null인 경우 오류를 반환한다.")
    void productIdIsNull() {
        assertThatCode(() -> new CartRequest(null))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("productId는 필수 항목입니다.");
    }

    @Test
    @DisplayName("productId가 0인 경우 오류를 반환한다.")
    void productIdIsZero() {
        assertThatCode(() -> new CartRequest(0L))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("productId는 양의 정수입니다.");
    }

    @Test
    @DisplayName("productId가 음수인 경우 오류를 반환한다.")
    void productIdIsNegative() {
        assertThatCode(() -> new CartRequest(-1L))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("productId는 양의 정수입니다.");
    }
}