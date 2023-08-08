package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

public class LoginRequest {

    private static final int PASSWORD_MAX_LENGTH = 100;
    private static final int EMAIL_MAX_LENGTH = 100;

    private final String email;
    private final String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("email") final String email,
            @JsonProperty("password") final String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
    }

    private void validate(String email, String password) {
        Assert.notNull(email, "이메일을 입력해야 합니다.");
        Assert.isTrue(!email.isBlank(), "이메일을 입력해야 합니다.");
        Assert.isTrue(email.length() <= EMAIL_MAX_LENGTH,
                String.format("이메일은 %d자 이하여야 합니다.", EMAIL_MAX_LENGTH));

        Assert.notNull(password, "비밀번호를 입력해야 합니다.");
        Assert.isTrue(!password.isBlank(), "비밀번호를 입력해야 합니다.");
        Assert.isTrue(password.length() <= PASSWORD_MAX_LENGTH,
                String.format("비밀번호는 %d자 이하여야 합니다.", PASSWORD_MAX_LENGTH));
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
