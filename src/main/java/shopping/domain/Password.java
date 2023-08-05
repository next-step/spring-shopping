package shopping.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.exception.ArgumentValidateFailException;

@Embeddable
public class Password {

    private String password;

    protected Password() {
    }

    public Password(String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if (password == null) {
            throw new ArgumentValidateFailException("비밀번호는 null일 수 없습니다.");
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
