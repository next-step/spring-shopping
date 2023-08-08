package shopping.mart.controller.config;

import org.springframework.stereotype.Component;
import shopping.auth.app.spi.AuthorizationPath;

@Component
public class CartAuthorizationPath implements AuthorizationPath {

    @Override
    public String path() {
        return "/carts";
    }
}
