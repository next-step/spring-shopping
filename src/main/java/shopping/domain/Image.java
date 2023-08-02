package shopping.domain;

import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;

@Embeddable
public class Image {

    private static final int MAX_LENGTH = 255;

    private final String value;

    protected Image() {
        this.value = null;
    }

    public Image(String value) {
        validateLength(value);

        this.value = value;
    }

    private void validateLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ShoppingException("이미지 주소는 255자를 넘을 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
