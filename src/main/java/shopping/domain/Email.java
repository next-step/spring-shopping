package shopping.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Email {

    private final String value;

    protected Email() {
        this.value = null;
    }

    public Email(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
