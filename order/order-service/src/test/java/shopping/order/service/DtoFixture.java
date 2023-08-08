package shopping.order.service;

import java.util.List;
import shopping.mart.app.api.cart.response.CartResponse.ProductResponse;

class DtoFixture {

    static final class CartResponse {

        static shopping.mart.app.api.cart.response.CartResponse defaultCartResponse() {
            ProductResponse productResponse = new ProductResponse(1L, 1, "images/default-image.png", "default-name",
                    "1000");
            return new shopping.mart.app.api.cart.response.CartResponse(1L, List.of(productResponse));
        }
    }

}
