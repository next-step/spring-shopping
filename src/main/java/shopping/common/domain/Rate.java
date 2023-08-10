package shopping.common.domain;

public class Rate {

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
}
