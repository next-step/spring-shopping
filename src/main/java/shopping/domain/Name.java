package shopping.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private final String value;

    protected Name() {
        this.value = null;
    }

    public Name(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
