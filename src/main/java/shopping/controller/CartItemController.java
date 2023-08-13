package shopping.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.CartItemService;
import shopping.auth.UserIdPrincipal;
import shopping.dto.web.request.CartItemCreateRequest;
import shopping.dto.web.request.CartItemUpdateRequest;
import shopping.dto.web.response.CartItemResponse;

@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/api/cart/items")
    public ResponseEntity<CartItemResponse> createCartItem(@UserIdPrincipal Long userId,
            @RequestBody CartItemCreateRequest cartItemCreateRequest) {
        CartItemResponse cartItemResponse = cartItemService.createCartItem(userId, cartItemCreateRequest);
        return ResponseEntity.created(URI.create("/api/cart/items/" + cartItemResponse.getId())).body(cartItemResponse);
    }

    @GetMapping("/api/cart/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@UserIdPrincipal Long userId) {
        List<CartItemResponse> cartItems = cartItemService.findAllByUserId(userId);
        return ResponseEntity.ok().body(cartItems);
    }

    @PatchMapping("/api/cart/items/{id}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(
            @UserIdPrincipal Long userId,
            @PathVariable Long id,
            @RequestBody CartItemUpdateRequest cartItemUpdateRequest) {
        CartItemResponse cartItemResponse = cartItemService.updateCartItemQuantity(userId, id, cartItemUpdateRequest);
        return ResponseEntity.ok().body(cartItemResponse);
    }

    @DeleteMapping("/api/cart/items/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @UserIdPrincipal Long userId,
            @PathVariable Long id) {
        cartItemService.deleteCartItem(userId, id);
        return ResponseEntity.noContent().build();
    }
}
