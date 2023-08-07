package shopping.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.CartProductService;
import shopping.dto.CartProductRequest;
import shopping.dto.CartProductResponse;
import shopping.ui.argumentresolver.Login;

@RestController
@RequestMapping("/cart")
public class CartProductRestController {

    private final CartProductService cartProductService;

    public CartProductRestController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @GetMapping("/products")
    public List<CartProductResponse> findCartProducts(@Login Long memberId) {
        return cartProductService.findCartProducts(memberId);
    }

    @PostMapping("/products/{productId}")
    public void addCartProduct(@PathVariable Long productId, @Login Long memberId) {
        cartProductService.addProduct(memberId, productId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCartProduct(@PathVariable Long id, @RequestBody CartProductRequest request) {
        cartProductService.updateCartProduct(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable Long id) {
        cartProductService.deleteCartProduct(id);
        return ResponseEntity.noContent().build();
    }
}
