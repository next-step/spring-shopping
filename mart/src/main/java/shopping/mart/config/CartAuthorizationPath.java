package shopping.mart.config;

import org.springframework.stereotype.Component;
import shopping.auth.config.AuthorizationPath;

@Component
public class CartAuthorizationPath implements AuthorizationPath {

    private CartAuthorizationPath() {
    }

    @Override
    public String path() {
        return "/carts";
    }
}
