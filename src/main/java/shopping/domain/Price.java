package shopping.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Price {

    private final int value;

    protected Price() {
        this.value = 0;
    }

    public Price(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
