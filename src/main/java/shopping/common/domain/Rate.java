package shopping.common.domain;

import java.util.Objects;

public class Rate {

    public static final Rate EMPTY_RATE = new Rate(-1);
    private final double value;

    public Rate(double value) {
        this.value = value;
    }

    public static Rate valueOf(double value) {
        return new Rate(value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Double.compare(rate.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Rate{" +
                "value=" + value +
                '}';
    }
}
