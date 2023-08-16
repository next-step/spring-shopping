package shopping.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LoginResponse {

    private final String accessToken;

    @JsonCreator
    public LoginResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }
}
