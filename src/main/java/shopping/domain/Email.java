package shopping.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;
import shopping.exception.MemberException;

@Embeddable
public class Email {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Column(name = "email")
    private String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    protected Email() {
    }

    public String getValue() {
        return value;
    }

    private void validate(String value) {
        if (!StringUtils.hasText(value)) {
            throw new MemberException("사용자 이메일이 존재하지 않습니다");
        }
        if (!pattern.matcher(value).matches()) {
            throw new MemberException("올바른 이메일 형식이 아닙니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
            "value='" + value + '\'' +
            '}';
    }
}
