package shopping.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class QuantityTest {

    @Test
    void increase() {
        // given
        Quantity quantity = new Quantity(4);

        // when
        Quantity newQuantity = quantity.increase();

        // then
        assertThat(newQuantity.getValue()).isEqualTo(5);
    }

    @Test
    void update() {
        // given
        Quantity quantity = new Quantity(4);

        // when
        Quantity newQuantity = quantity.update(6);

        // then
        assertThat(newQuantity.getValue()).isEqualTo(6);
    }

    @Test
    @DisplayName("Quantity의 값을 반환한다.")
    void getValue() {
        // given
        Quantity quantity = new Quantity(4);

        // when, then
        assertThat(quantity.getValue()).isEqualTo(4);
    }

    @Test
    @DisplayName("Quantity가 0인 경우 오류를 반환한다.")
    void valueZero() {
        assertThatCode(() -> new Quantity(0))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("수량은 0보다 작거나 같을 수 없습니다.");
    }

    @Test
    @DisplayName("Quantity가 음수인 경우 오류를 반환한다.")
    void valueNegative() {
        assertThatCode(() -> new Quantity(-1))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("수량은 0보다 작거나 같을 수 없습니다.");
    }
}
