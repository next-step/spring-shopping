package shopping.mart.app.spi;

import shopping.mart.app.domain.Cart;

public interface CartRepository {

    void persistCart(Cart cart);

    boolean existCartByUserId(Long userId);

    Cart newCart(long userId);

    Cart getByUserId(long userId);

    Cart getById(long cartId);
}
