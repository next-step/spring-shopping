package shopping.product.domain.vo;

import java.math.BigDecimal;
import javax.persistence.Embeddable;

@Embeddable
public class Money {

    private BigDecimal amount;

    protected Money() {
    }

    public Money(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public boolean isMoreThan(Money otherMoney) {
        return this.amount.compareTo(otherMoney.amount) > 0;
    }

    public boolean isSmallerThan(Money otherMoney) {
        return this.amount.compareTo(otherMoney.amount) < 0;
    }
}
