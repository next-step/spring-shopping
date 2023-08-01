package shopping.persist;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import shopping.domain.Cart;

@Repository
public class CartRepository {

    public Optional<Cart> findByUserId(long userId) {
        return Optional.empty();
    }

    public void updateCart(Cart cart) {
    }
}
