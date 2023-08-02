package shopping.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shopping.application.CartService;
import shopping.auth.RequestToken;
import shopping.dto.CartItemCreateRequest;
import shopping.dto.CartItemResponse;
import shopping.dto.CartItemUpdateRequest;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "cart";
    }

    @PostMapping("/cart/items")
    public ResponseEntity<Void> createCartItem(
            @RequestToken String email,
            @RequestBody CartItemCreateRequest cartItemCreateRequest) {

        cartService.createCartItem(email, cartItemCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@RequestToken String email) {

        List<CartItemResponse> cartItems = cartService.findAllByEmail(email);
        return ResponseEntity.ok().body(cartItems);
    }

    @PatchMapping("/cart/items/{id}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @RequestToken String email,
            @PathVariable Long id,
            @RequestBody CartItemUpdateRequest cartItemUpdateRequest
    ) {

        cartService.updateCartItemQuantity(email, id, cartItemUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
