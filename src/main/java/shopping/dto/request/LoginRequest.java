package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;
import shopping.exception.RequestException;

public class LoginRequest {

    private final String email;
    private final String password;


    @JsonCreator
    public LoginRequest(@JsonProperty String email, @JsonProperty String password) {
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
    }
}
