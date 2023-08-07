package shopping.common.vo;

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
}
