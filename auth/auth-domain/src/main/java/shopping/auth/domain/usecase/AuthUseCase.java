package shopping.auth.domain.usecase;

import shopping.auth.domain.usecase.request.LoginRequest;
import shopping.auth.domain.usecase.response.TokenResponse;

public interface AuthUseCase {

    TokenResponse authenticate(LoginRequest request);

}
