package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ArgumentValidateFailException;

@DisplayName("환율 도메인 테스트")
class RatioTest {

    @DisplayName("환율 정상 생성 성공")
    @Test
    void ratioCreateSuccess() {
        // given
        Double ratio = 1.0;

        // when, then
        assertThatCode(() -> new Ratio(ratio))
                .doesNotThrowAnyException();
    }

    @DisplayName("null일시 환율 정상 생성 실패")
    @Test
    void ratioNullCreateFail() {
        // given
        Double ratio = null;

        // when, then
        assertThatCode(() -> new Ratio(ratio))
                .isInstanceOf(ArgumentValidateFailException.class);
    }

    @DisplayName("양수가 아닐시 환율 정상 생성 실패")
    @Test
    void ratioNegativeCreateFail() {
        // given
        Double ratio = -1.0;

        // when, then
        assertThatCode(() -> new Ratio(ratio))
                .isInstanceOf(ArgumentValidateFailException.class);
    }
}
