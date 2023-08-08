package shopping.order.controller.config;

import org.springframework.stereotype.Component;
import shopping.auth.service.config.AuthorizationPath;

@Component
public class OrderAuthorizationPath implements AuthorizationPath {

    @Override
    public String path() {
        return "/orders";
    }
}
