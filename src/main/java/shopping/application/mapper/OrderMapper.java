package shopping.application.mapper;

import org.springframework.stereotype.Component;
import shopping.domain.entity.CartItem;
import shopping.domain.entity.OrderItem;
import shopping.domain.entity.Product;

@Component
public class OrderMapper {

    public OrderItem mapItemFrom(final CartItem cartItem) {
        final Product product = cartItem.getProduct();
        return new OrderItem(
                product.getId(),
                product.getName(),
                product.getImage(),
                product.getPrice(),
                cartItem.getQuantity()
        );
    }
}
