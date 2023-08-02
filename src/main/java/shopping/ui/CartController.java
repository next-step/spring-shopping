package shopping.ui;

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
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.CartUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody CartCreateRequest request,
                                           @AuthenticationPrincipal Long userId) {
        cartService.addProduct(request, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> findAll(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok().body(cartService.findAll(userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateQuantity(@RequestBody CartUpdateRequest request,
                                               @PathVariable Long id,
                                               @AuthenticationPrincipal Long userId) {
        cartService.update(request, id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal Long userId) {
        cartService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
