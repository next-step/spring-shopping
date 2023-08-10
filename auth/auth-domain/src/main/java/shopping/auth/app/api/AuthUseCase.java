package shopping.auth.app.api;

import shopping.auth.app.api.request.LoginRequest;
import shopping.auth.app.api.response.TokenResponse;

public interface AuthUseCase {

    TokenResponse authenticate(LoginRequest request);

}
