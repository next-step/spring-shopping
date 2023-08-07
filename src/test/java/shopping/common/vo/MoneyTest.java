package shopping.common.vo;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("두 Money의 합을 반환한다.")
    void add() {
        Money money = new Money("123.45");
        Money otherMoney = new Money("100.01");

        Money addMoney = money.add(otherMoney);

        Assertions.assertThat(addMoney).extracting("amount").isEqualTo(new BigDecimal("223.46"));
    }

    @Test
    @DisplayName("곱하고 싶은 숫자를 넣으면 Money의 곱 결과를 반환한다.")
    void multiply() {
        Money money = new Money("123.01");
        int number = 3;

        Money multiplyMoney = money.multiply(number);

        Assertions.assertThat(multiplyMoney).extracting("amount").isEqualTo(new BigDecimal("369.03"));
    }
}
