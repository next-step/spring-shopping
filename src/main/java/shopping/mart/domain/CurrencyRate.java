package shopping.mart.domain;

import java.util.Objects;

public class CurrencyRate {

    private final Double value;

    public CurrencyRate(final Double value) {
        this.value = !Objects.isNull(value) && value > 0
                ? value
                : null;
    }

    public static CurrencyRate empty() {
        return new CurrencyRate(null);
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyRate that = (CurrencyRate) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
