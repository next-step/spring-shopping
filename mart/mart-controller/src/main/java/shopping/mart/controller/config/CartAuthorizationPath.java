package shopping.mart.controller.config;

import org.springframework.stereotype.Component;
import shopping.auth.domain.usecase.AuthorizationPath;

@Component
public class CartAuthorizationPath implements AuthorizationPath {

    @Override
    public String path() {
        return "/carts";
    }
}
