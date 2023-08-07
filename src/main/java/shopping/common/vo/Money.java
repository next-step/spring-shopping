package shopping.common.vo;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Money {

    @Column
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

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return "Money{" +
            "amount=" + amount +
            '}';
    }
}
