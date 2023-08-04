package shopping.auth.domain;

import java.text.MessageFormat;
import shopping.auth.domain.status.UserExceptionStatus;
import shopping.core.exception.StatusCodeException;

public final class User {

    public static final int MAXIMUM_EMAIL_LENGTH = 100;
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String SPECIAL_CHARACTER_REGEX =
            ".*[!@#$%^&*(),.?\":{}|<>].*";
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    private static final int MAXIMUM_PASSWORD_LENGTH = 100;
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
        if (isValidEmail(email)) {
            return;
        }
        throw new StatusCodeException(MessageFormat.format("\"{0}\" 은 사용할 수 없는 이메일입니다.", email),
                UserExceptionStatus.INVALID_EMAIL.getStatus());
    }

    private boolean isValidEmail(String email) {
        return email.length() <= MAXIMUM_EMAIL_LENGTH && email.matches(EMAIL_REGEX);
    }

    private void validatePassword(final String password) {
        if (isValidPassword(password)) {
            return;
        }
        throw new StatusCodeException(MessageFormat.format("비밀번호 \"{0}\" 는 특수문자를 포함하면서 {1} 자 이상이어야 합니다.",
                password, MINIMUM_PASSWORD_LENGTH), UserExceptionStatus.INVALID_PASSWORD.getStatus());
    }

    private boolean isValidPassword(String password) {
        return MINIMUM_PASSWORD_LENGTH <= password.length() && password.length() <= MAXIMUM_PASSWORD_LENGTH
                && password.matches(SPECIAL_CHARACTER_REGEX);
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
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void assertPassword(final String password) {
        if (this.password.equals(password)) {
            return;
        }
        throw new StatusCodeException("비밀번호가 일치하지 않습니다.", UserExceptionStatus.LOGIN_FAILED.getStatus());
    }
}
