package shopping.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shopping.application.CartService;
import shopping.auth.EmailInToken;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;

import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/items")
    public ResponseEntity<Void> createCartItem(
            @EmailInToken String email,
            @RequestBody CartItemCreateRequest cartItemCreateRequest) {

        cartService.createCartItem(email, cartItemCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@EmailInToken String email) {
        List<CartItemResponse> cartItems = cartService.findAllByEmail(email);
        return ResponseEntity.ok().body(cartItems);
    }

    @PatchMapping("/cart/items/{id}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @EmailInToken String email,
            @PathVariable Long id,
            @RequestBody CartItemUpdateRequest cartItemUpdateRequest) {
        cartService.updateCartItemQuantity(email, id, cartItemUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @EmailInToken String email,
            @PathVariable Long id) {
        cartService.deleteCartItem(email, id);
        return ResponseEntity.noContent().build();
    }
}
