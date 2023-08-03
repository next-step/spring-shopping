package shopping.dto.request;

import org.springframework.util.StringUtils;
import shopping.exception.MemberException;
import shopping.exception.RequestException;
import shopping.utils.EmailUtils;

public class LoginRequest {

    private String email;
    private String password;

    private LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        validateEmail(email);
        validatePassword(password);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new RequestException("비밀번호 입력이 존재하지 않습니다");
        }
    }

    private void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new RequestException("이메일 입력이 존재하지 않습니다");
        }
        if (!EmailUtils.isValidEmail(email)) {
            throw new RequestException("올바른 이메일 형식이 아닙니다");
        }
    }
}
