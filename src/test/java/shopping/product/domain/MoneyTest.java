package shopping.product.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.product.domain.vo.Money;

@DisplayName("Money 단위 테스트")
class MoneyTest {

    @Test
    @DisplayName("Money가 비교할 Money보다 값이 크면 true를 반환한다.")
    void moneyIsMoreThanSmallMoney() {
        Money bigMoney = new Money("1");
        Money smallMoney = new Money("0");

        Assertions.assertThat(bigMoney.isMoreThan(smallMoney)).isTrue();
        Assertions.assertThat(smallMoney.isMoreThan(bigMoney)).isFalse();
    }

    @Test
    @DisplayName("Money가 비교할 Money보다 값이 작으면 true를 반환한다.")
    void moneyIsSmallerThanBigMoney() {
        Money bigMoney = new Money("1");
        Money smallMoney = new Money("0");

        Assertions.assertThat(smallMoney.isSmallerThan(bigMoney)).isTrue();
        Assertions.assertThat(bigMoney.isSmallerThan(smallMoney)).isFalse();
    }

    @Test
    @DisplayName("두 Money를 합한다")
    void plusMoney() {
        Money money1 = new Money("1");
        Money money2 = new Money("2");

        Assertions.assertThat(money1.plus(money2)).isEqualTo(new Money("3"));
    }

    @Test
    @DisplayName("Money에 특정 수를 곱한다")
    void multiplyMoney() {
        Money money = new Money("1");
        int multiplier = 2;

        Assertions.assertThat(money.multiply(multiplier)).isEqualTo(new Money("2"));
    }
}
