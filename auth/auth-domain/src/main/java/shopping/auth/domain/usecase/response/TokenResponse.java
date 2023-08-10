package shopping.auth.domain.usecase.response;

public final class TokenResponse {

    private String accessToken;

    public TokenResponse() {
    }

    public TokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
