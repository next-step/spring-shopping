package shopping.domain.user;

import javax.persistence.Embeddable;

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
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다.");
        }
    }

    public String getPassword() {
        return password;
    }
}
