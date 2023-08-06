package shopping.domain.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.InvalidRequestException;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("가격 도메인 테스트")
class PriceTest {

    @DisplayName("가격은 양수여야 한다.")
    @Test
    void positivePrice() {
        assertThatNoException().isThrownBy(() -> new Price(1000L));
    }

    @DisplayName("가격이 0이하이면 예외를 던진다.")
    @Test
    void notPositivePrice() {
        assertThatThrownBy(() -> new Price(0L))
                .isInstanceOf(InvalidRequestException.class);
    }
}