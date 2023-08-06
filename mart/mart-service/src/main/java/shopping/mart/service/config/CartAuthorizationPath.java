package shopping.mart.service.config;

import org.springframework.stereotype.Component;
import shopping.auth.service.config.AuthorizationPath;

@Component
public class CartAuthorizationPath implements AuthorizationPath {

    @Override
    public String path() {
        return "/carts";
    }
}
