package shopping.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.ShoppingException;

@Embeddable
public class MemberEmail {

    private static final Pattern emailPattern = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private String email;

    protected MemberEmail() {
    }

    public MemberEmail(final String email) {
        validateIsNotNullOrEmpty(email);
        validateEmailFormat(email);

        this.email = email;
    }

    private void validateIsNotNullOrEmpty(final String email) {
        if (!StringUtils.hasText(email)) {
            throw new ShoppingException("회원 이메일은 비어있거나 공백이면 안됩니다. 입력값: " + email);
        }
    }

    private void validateEmailFormat(final String email) {
        if (!emailPattern.matcher(email).matches()) {
            throw new ShoppingException("회원 이메일이 형식에 맞지 않습니다. 입력값: " + email);
        }
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberEmail that = (MemberEmail) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
