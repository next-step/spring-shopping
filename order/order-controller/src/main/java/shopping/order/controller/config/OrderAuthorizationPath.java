package shopping.order.controller.config;

import org.springframework.stereotype.Component;
import shopping.auth.app.spi.AuthorizationPath;

@Component
public class OrderAuthorizationPath implements AuthorizationPath {

    @Override
    public String path() {
        return "/orders";
    }
}
