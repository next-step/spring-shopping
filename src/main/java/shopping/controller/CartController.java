package shopping.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.application.CartService;
import shopping.auth.EmailPrincipal;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;

import java.net.URI;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/items")
    public ResponseEntity<CartItemResponse> createCartItem(@EmailPrincipal String email,
            @RequestBody CartItemCreateRequest cartItemCreateRequest) {
        CartItemResponse cartItemResponse = cartService.createCartItem(email, cartItemCreateRequest);
        return ResponseEntity.created(URI.create("/cart/items/" + cartItemResponse.getId())).body(cartItemResponse);
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@EmailPrincipal String email, @PageableDefault Pageable pageable) {
        List<CartItemResponse> cartItems = cartService.findAllByEmail(email, pageable);
        return ResponseEntity.ok().body(cartItems);
    }

    @PatchMapping("/cart/items/{id}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(
            @EmailPrincipal String email,
            @PathVariable Long id,
            @RequestBody CartItemUpdateRequest cartItemUpdateRequest) {
        CartItemResponse cartItemResponse = cartService.updateCartItemQuantity(email, id, cartItemUpdateRequest);
        return ResponseEntity.ok().body(cartItemResponse);
    }

    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @EmailPrincipal String email,
            @PathVariable Long id) {
        cartService.deleteCartItem(email, id);
        return ResponseEntity.noContent().build();
    }
}
