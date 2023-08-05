package shopping.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.application.CartItemService;
import shopping.auth.EmailPrincipal;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;

import java.net.URI;
import java.util.List;

@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/cart/items")
    public ResponseEntity<CartItemResponse> createCartItem(@EmailPrincipal String email,
            @RequestBody CartItemCreateRequest cartItemCreateRequest) {
        CartItemResponse cartItemResponse = cartItemService.createCartItem(email, cartItemCreateRequest);
        return ResponseEntity.created(URI.create("/cart/items/" + cartItemResponse.getId())).body(cartItemResponse);
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@EmailPrincipal String email, @PageableDefault Pageable pageable) {
        List<CartItemResponse> cartItems = cartItemService.findAllByEmail(email, pageable);
        return ResponseEntity.ok().body(cartItems);
    }

    @PatchMapping("/cart/items/{id}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(
            @EmailPrincipal String email,
            @PathVariable Long id,
            @RequestBody CartItemUpdateRequest cartItemUpdateRequest) {
        CartItemResponse cartItemResponse = cartItemService.updateCartItemQuantity(email, id, cartItemUpdateRequest);
        return ResponseEntity.ok().body(cartItemResponse);
    }

    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @EmailPrincipal String email,
            @PathVariable Long id) {
        cartItemService.deleteCartItem(email, id);
        return ResponseEntity.noContent().build();
    }
}
