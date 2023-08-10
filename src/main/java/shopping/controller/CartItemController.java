package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.application.CartItemService;
import shopping.auth.UserIdPrincipal;
import shopping.dto.web.request.CartItemCreateRequest;
import shopping.dto.web.request.CartItemUpdateRequest;
import shopping.dto.web.response.CartItemResponse;

import java.net.URI;
import java.util.List;

@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/cart/items")
    public ResponseEntity<CartItemResponse> createCartItem(@UserIdPrincipal Long userId,
            @RequestBody CartItemCreateRequest cartItemCreateRequest) {
        CartItemResponse cartItemResponse = cartItemService.createCartItem(userId, cartItemCreateRequest);
        return ResponseEntity.created(URI.create("/cart/items/" + cartItemResponse.getId())).body(cartItemResponse);
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@UserIdPrincipal Long userId) {
        List<CartItemResponse> cartItems = cartItemService.findAllByUserId(userId);
        return ResponseEntity.ok().body(cartItems);
    }

    @PatchMapping("/cart/items/{id}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(
            @UserIdPrincipal Long userId,
            @PathVariable Long id,
            @RequestBody CartItemUpdateRequest cartItemUpdateRequest) {
        CartItemResponse cartItemResponse = cartItemService.updateCartItemQuantity(userId, id, cartItemUpdateRequest);
        return ResponseEntity.ok().body(cartItemResponse);
    }

    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @UserIdPrincipal Long userId,
            @PathVariable Long id) {
        cartItemService.deleteCartItem(userId, id);
        return ResponseEntity.noContent().build();
    }
}
