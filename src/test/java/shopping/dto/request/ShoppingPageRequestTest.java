package shopping.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@DisplayName("페이지 요청 dto 테스트")
class ShoppingPageRequestTest {

    @DisplayName("정상 입력 정상 반환")
    @Test
    void normalPageNumberAndPageSize() {
        // given
        int pageNumber = 1;
        int pageSize = 12;

        // when
        ShoppingPageRequest pageRequest = ShoppingPageRequest.of(pageNumber, pageSize);

        // then
        assertThat(pageRequest).extracting(
                ShoppingPageRequest::getPageNumber,
                ShoppingPageRequest::getPageSize
        ).containsExactly(pageNumber - 1, pageSize);
    }

    @DisplayName("페이지 넘버가 작으면 최소값 반환")
    @Test
    void smallPageNumberThenPageNumberIsMinimum() {
        // given
        int pageNumber = -1;
        int pageSize = 12;

        // when
        ShoppingPageRequest pageRequest = ShoppingPageRequest.of(pageNumber, pageSize);

        // then
        assertThat(pageRequest).extracting(
                ShoppingPageRequest::getPageNumber,
                ShoppingPageRequest::getPageSize
        ).containsExactly(0, pageSize);
    }

    @DisplayName("사이즈가 너무 작으면 최소값 반환")
    @Test
    void smallPageSizeThenMinimumPageSize() {
        // given
        int pageNumber = 1;
        int pageSize = -12;

        // when
        ShoppingPageRequest pageRequest = ShoppingPageRequest.of(pageNumber, pageSize);

        // then
        assertThat(pageRequest).extracting(
                ShoppingPageRequest::getPageNumber,
                ShoppingPageRequest::getPageSize
        ).containsExactly(pageNumber - 1, 6);
    }

    @DisplayName("사이즈가 너무 크면 최대값 반환")
    @Test
    void largePageSizeThenMaximumPageSize() {
        // given
        int pageNumber = 1;
        int pageSize = 120;

        // when
        ShoppingPageRequest pageRequest = ShoppingPageRequest.of(pageNumber, pageSize);

        // then
        assertThat(pageRequest).extracting(
                ShoppingPageRequest::getPageNumber,
                ShoppingPageRequest::getPageSize
        ).containsExactly(pageNumber - 1, 30);
    }

    @DisplayName("최신 기준으로 생성하면 역순 정렬 반환")
    @Test
    void whenRecentOfThenReturnDESCSort() {
        // given
        int pageNumber = 1;
        int pageSize = 12;

        // when
        ShoppingPageRequest pageRequest = ShoppingPageRequest.recentPageOf(pageNumber, pageSize);

        // then
        assertThat(pageRequest).extracting(
                ShoppingPageRequest::getPageNumber,
                ShoppingPageRequest::getPageSize,
                ShoppingPageRequest::getSort
        ).containsExactly(pageNumber - 1, 12, Sort.by(Direction.DESC, "id"));
    }
}
