package shopping.dto.response;

public class LoginResponse {
    private String accessToken;

    public LoginResponse() {
    }

    private LoginResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public static LoginResponse of(final String accessToken) {
        return new LoginResponse(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }
}
