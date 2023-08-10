package shopping.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.application.CartService;
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.QuantityUpdateRequest;
import shopping.ui.config.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody final CartCreateRequest request,
                                           @AuthenticationPrincipal final Long userId) {
        cartService.addProduct(request, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> findAll(@AuthenticationPrincipal final Long userId) {
        return ResponseEntity.ok().body(cartService.findAll(userId));
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<Void> updateQuantity(@RequestBody final QuantityUpdateRequest request,
                                               @PathVariable final Long id,
                                               @AuthenticationPrincipal final Long userId) {
        cartService.update(request, id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id,
                                       @AuthenticationPrincipal final Long userId) {
        cartService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
