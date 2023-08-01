package shopping.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.CartService;
import shopping.dto.CartRequest;
import shopping.infrastructure.JwtProvider;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final JwtProvider jwtProvider;
    private final CartService cartService;

    public CartController(JwtProvider jwtProvider, CartService cartService) {
        this.jwtProvider = jwtProvider;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody CartRequest request,
                                           @RequestHeader(value = "Authorization") String bearerToken) {
        final String token = bearerToken.split(" ")[1];
        cartService.addProduct(request, Long.parseLong(jwtProvider.getPayload(token)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
