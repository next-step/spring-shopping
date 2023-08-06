package shopping.domain.member;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Embeddable
public class Nickname {

    private static final int MAX_NICKNAME_LENGTH = 10;

    @Column(name = "nickname", length = 10, nullable = false, unique = true)
    private String value;

    protected Nickname() {
    }

    private Nickname(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (!StringUtils.hasText(value) || value.length() > MAX_NICKNAME_LENGTH) {
            throw new ShoppingException(ErrorCode.NICKNAME_INVALID);
        }
    }

    public static Nickname from(final String value) {
        return new Nickname(value);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Nickname nickname = (Nickname) o;
        return Objects.equals(value, nickname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
