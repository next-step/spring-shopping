package shopping.order.controller.config;

import org.springframework.stereotype.Component;
import shopping.auth.domain.usecase.AuthorizationPath;

@Component
public class ReceiptDetailAuthorizationPath implements AuthorizationPath {

    @Override
    public String path() {
        return "/receipts/*";
    }

}
