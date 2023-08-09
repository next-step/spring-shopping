package shopping.product.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private BigDecimal amount;

    protected Money() {
    }

    public Money(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money plus(Money otherMoney) {
        return new Money(this.amount.add(otherMoney.amount));
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

    private void setAmount(BigDecimal amount) { //for hibernate
        this.amount = amount;
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
