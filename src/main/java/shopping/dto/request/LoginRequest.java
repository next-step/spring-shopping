package shopping.dto.request;

import static shopping.dto.request.validator.RequestArgumentValidator.validateStringArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
        validateStringArgument(email, "이메일", EMAIL_MAX_LENGTH);
        validateStringArgument(password, "비밀번호", PASSWORD_MAX_LENGTH);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
