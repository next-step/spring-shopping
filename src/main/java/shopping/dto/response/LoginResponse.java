package shopping.dto.response;

public class LoginResponse {

    private final String accessToken;

    public LoginResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }
}
