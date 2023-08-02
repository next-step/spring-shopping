package shopping.cart.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.cart.domain.CartItem;
import shopping.cart.dto.ProductCartItemDto;
import shopping.product.domain.Product;

public class CartItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private String imageUrl;
    private int quantity;

    private CartItemResponse() {
    }

    public CartItemResponse(Long id, Long productId, String name, String imageUrl, int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static CartItemResponse from(CartItem cartItem, Product product) {
        return new CartItemResponse(cartItem.getId(), cartItem.getProductId(),
            cartItem.getProductName(), product.getImage().toUrl(), cartItem.getQuantity());
    }

    public static List<CartItemResponse> listOf(List<ProductCartItemDto> productCartItemDtos) {
        return productCartItemDtos.stream()
            .map(productCartItemDto ->
                from(productCartItemDto.getCartItem(), productCartItemDto.getProduct()))
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
