package shopping.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ShoppingException;

@Embeddable
public class MemberPassword {

    public static final int MAX_LENGTH = 30;

    private String value;

    protected MemberPassword() {
    }

    public MemberPassword(final String value) {
        validateIsNotNullOrEmpty(value);
        validateGreaterThanMaxLength(value);

        this.value = value;
    }

    private void validateGreaterThanMaxLength(final String password) {
        if (password.length() > MAX_LENGTH) {
            throw new ShoppingException("회원 비밀번호는 30자 이하여야 합니다. 입력값: " + password);
        }
    }

    private void validateIsNotNullOrEmpty(final String password) {
        if (!StringUtils.hasText(password)) {
            throw new ShoppingException("회원 비밀번호는 비어있거나 공백이면 안됩니다. 입력값: " + password);
        }
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
        final MemberPassword that = (MemberPassword) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
