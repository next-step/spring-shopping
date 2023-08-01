package shopping.domain;

import java.text.MessageFormat;
import java.util.Objects;
import shopping.domain.exception.StatusCodeException;
import shopping.domain.status.UserExceptionStatus;

// TODO: EMAIL, PASSWORD 최소, 최대길이 검증 로직  추가
public final class User {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String SPECIAL_CHARACTER_REGEX =
            ".*[!@#$%^&*(),.?\":{}|<>].*";
    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    private final Long id;
    private final String email;
    private final String password;

    public User(final String email, final String password) {
        this(null, email, password);
    }

    public User(final Long id, final String email, final String password) {
        this.id = id;
        validateEmail(email);
        validatePassword(password);
        this.email = email;
        this.password = password;
    }

    private void validateEmail(final String email) {
        if (email.matches(EMAIL_REGEX)) {
            return;
        }
        throw new StatusCodeException(MessageFormat.format("\"{0}\" 은 사용할 수 없는 이메일입니다.", email),
                UserExceptionStatus.INVALID_EMAIL.getStatus());
    }

    private void validatePassword(final String password) {
        if (password.length() >= MINIMUM_PASSWORD_LENGTH && password.matches(SPECIAL_CHARACTER_REGEX)) {
            return;
        }
        throw new StatusCodeException(MessageFormat.format("비밀번호 \"{0}\" 는 특수문자를 포함하면서 {1} 자 이상이어야 합니다.",
                password, MINIMUM_PASSWORD_LENGTH), UserExceptionStatus.INVALID_PASSWORD.getStatus());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
