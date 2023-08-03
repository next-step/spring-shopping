package shopping.domain;


import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ArgumentValidateFailException;

@DisplayName("가격 도메인 테스트")
class PriceTest {

    @DisplayName("가격 정상 생성 성공")
    @Test
    void priceCreateSuccess() {
        // given
        long price = 1L;

        // when, then
        assertThatCode(() -> new Price(price))
                .doesNotThrowAnyException();
    }

    @DisplayName("null일시 가격 정상 생성 실패")
    @Test
    void priceNullCreateFail() {
        // given
        Long price = null;

        // when, then
        assertThatCode(() -> new Price(price))
                .isInstanceOf(ArgumentValidateFailException.class);
    }

    @DisplayName("양수가 아닐시 가격 정상 생성 실패")
    @Test
    void priceNegativeCreateFail() {
        // given
        long price = -1L;

        // when, then
        assertThatCode(() -> new Price(price))
                .isInstanceOf(ArgumentValidateFailException.class);
    }
}
