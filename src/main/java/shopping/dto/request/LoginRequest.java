package shopping.dto.request;

import static shopping.dto.request.validator.RequestArgumentValidator.validateStringArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

    private static final int PASSWORD_MAX_LENGTH = 100;
    private static final int EMAIL_MAX_LENGTH = 100;
    private static final String EMAIL_NAME = "email";
    private static final String PASSWORD_NAME = "password";

    private final String email;
    private final String password;

    @JsonCreator
    public LoginRequest(@JsonProperty(EMAIL_NAME) final String email,
            @JsonProperty(PASSWORD_NAME) final String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
    }

    private void validate(String email, String password) {
        validateStringArgument(email, EMAIL_NAME, EMAIL_MAX_LENGTH);
        validateStringArgument(password, PASSWORD_NAME, PASSWORD_MAX_LENGTH);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
