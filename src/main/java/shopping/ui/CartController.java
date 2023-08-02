package shopping.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.CartService;
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.CartUpdateRequest;
import shopping.infrastructure.JwtProvider;

import java.util.List;

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
    public ResponseEntity<Void> addProduct(@RequestBody CartCreateRequest request,
                                           @RequestHeader(value = "Authorization") String bearerToken) {
        final String token = bearerToken.split(" ")[1];
        cartService.addProduct(request, Long.parseLong(jwtProvider.getPayload(token)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> findAll(@RequestHeader(value = "Authorization") String bearerToken) {
        final String token = bearerToken.split(" ")[1];
        return ResponseEntity.ok().body(cartService.findAll(Long.parseLong(jwtProvider.getPayload(token))));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateQuantity(@RequestBody CartUpdateRequest request,
                                               @PathVariable Long id,
                                               @RequestHeader(value = "Authorization") String bearerToken) {
        final String token = bearerToken.split(" ")[1];
        cartService.update(request, id, Long.parseLong(jwtProvider.getPayload(token)));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestHeader(value = "Authorization") String bearerToken) {
        final String token = bearerToken.split(" ")[1];
        cartService.delete(id, Long.parseLong(jwtProvider.getPayload(token)));
        return ResponseEntity.noContent().build();
    }
}