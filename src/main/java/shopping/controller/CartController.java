package shopping.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shopping.application.CartService;
import shopping.dto.CartItemCreateRequest;
import shopping.dto.CartItemResponse;

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
            HttpServletRequest request,
            @RequestBody CartItemCreateRequest cartItemCreateRequest) {

        String email = (String) request.getAttribute("email");
        cartService.createCartItem(email, cartItemCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cart/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(
            HttpServletRequest request) {

        String email = (String) request.getAttribute("email");
        List<CartItemResponse> cartItems = cartService.findAllByEmail(email);
        return ResponseEntity.ok().body(cartItems);
    }
}
