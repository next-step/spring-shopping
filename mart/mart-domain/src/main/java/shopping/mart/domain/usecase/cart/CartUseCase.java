package shopping.mart.domain.usecase.cart;

import shopping.mart.domain.usecase.cart.request.CartAddRequest;
import shopping.mart.domain.usecase.cart.request.CartUpdateRequest;
import shopping.mart.domain.usecase.cart.response.CartResponse;

public interface CartUseCase {

    void addProduct(long userId, CartAddRequest request);

    void updateProduct(long userId, CartUpdateRequest request);

    void deleteProduct(long userId, long productId);

    CartResponse getCart(long userId);

    void clearCart(long userId);
}
