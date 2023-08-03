package shopping.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.CartService;
import shopping.auth.RequestToken;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;

@RestController
@RequestMapping("/cart/items")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping
    public ResponseEntity<Void> createCartItem(
            @RequestToken String email,
            @RequestBody CartItemCreateRequest cartItemCreateRequest) {

        cartService.createCartItem(email, cartItemCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@RequestToken String email) {

        List<CartItemResponse> cartItems = cartService.findAllByEmail(email);
        return ResponseEntity.ok().body(cartItems);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @RequestToken String email,
            @PathVariable Long id,
            @RequestBody CartItemUpdateRequest cartItemUpdateRequest) {

        cartService.updateCartItemQuantity(email, id, cartItemUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @RequestToken String email,
            @PathVariable Long id) {

        cartService.deleteCartItem(email, id);
        return ResponseEntity.noContent().build();
    }
}
