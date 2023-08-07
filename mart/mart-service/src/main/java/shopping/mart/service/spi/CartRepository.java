package shopping.mart.service.spi;

import shopping.mart.domain.Cart;

public interface CartRepository {

    void persistCart(Cart cart);

    boolean existCartByUserId(Long userId);

    Cart newCart(long userId);

    Cart getByUserId(long userId);
}
