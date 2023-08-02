package shopping.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Image {

    private final String value;

    protected Image() {
        this.value = null;
    }

    public Image(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
