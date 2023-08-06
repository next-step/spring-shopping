package shopping.auth.service.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class TokenPerRequest {
    private String decryptedToken;

    void setDecryptedToken(String decryptedToken) {
        this.decryptedToken = decryptedToken;
    }

    public String getDecryptedToken() {
        return decryptedToken;
    }
}
