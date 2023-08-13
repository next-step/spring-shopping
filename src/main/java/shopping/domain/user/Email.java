package shopping.domain.user;

import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import shopping.exception.general.InvalidRequestException;

@Embeddable
public class Email {

    private static final String EMAIL_PATTERN = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private String email;

    protected Email() {
    }

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    private void validate(String email) {
        if (email == null) {
            throw new InvalidRequestException("이메일은 null 일수 없습니다.");
        }
        if (!Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
            throw new InvalidRequestException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
