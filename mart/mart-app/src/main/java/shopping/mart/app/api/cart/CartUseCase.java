package shopping.mart.app.api.cart;

import shopping.mart.app.api.cart.event.CartOrderedEvent;
import shopping.mart.app.api.cart.request.CartAddRequest;
import shopping.mart.app.api.cart.request.CartUpdateRequest;
import shopping.mart.app.api.cart.response.CartResponse;

public interface CartUseCase {

    void addProduct(long userId, CartAddRequest request);

    void updateProduct(long userId, CartUpdateRequest request);

    void deleteProduct(long userId, long productId);

    CartResponse getCart(long userId);

    void cartOrdered(CartOrderedEvent cartOrderedEvent);
}