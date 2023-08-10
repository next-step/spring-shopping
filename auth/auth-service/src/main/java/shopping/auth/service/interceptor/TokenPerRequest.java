package shopping.auth.service.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import shopping.auth.domain.usecase.Token;

@Component
@RequestScope
public class TokenPerRequest implements Token {
    private String decryptedToken;

    public void setDecryptedToken(String decryptedToken) {
        this.decryptedToken = decryptedToken;
    }

    @Override
    public String decrypted() {
        return decryptedToken;
    }
}
