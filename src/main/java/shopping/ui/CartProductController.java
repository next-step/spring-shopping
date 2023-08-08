package shopping.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.application.CartProductService;
import shopping.domain.CartProduct;
import shopping.dto.request.UpdateCartProductRequest;
import shopping.dto.response.FindCartProductResponse;
import shopping.ui.argumentresolver.Login;

@Controller
@RequestMapping("/cart")
public class CartProductController {

    private final CartProductService cartProductService;

    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @ResponseBody
    @GetMapping("/products")
    public List<FindCartProductResponse> findCartProducts(@Login Long memberId) {
        return cartProductService.findCartProducts(memberId);
    }

    @GetMapping
    public String findCart() {
        return "cart";
    }

    @ResponseBody
    @PostMapping("/products/{productId}")
    public ResponseEntity<Void> addCartProduct(@PathVariable Long productId, @Login Long memberId) {
        CartProduct cartProduct = cartProductService.addProduct(memberId, productId);
        return ResponseEntity.created(URI.create("/products/" + cartProduct.getId())).build();
    }

    @ResponseBody
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCartProduct(@PathVariable Long id, @RequestBody UpdateCartProductRequest request) {
        cartProductService.updateCartProduct(id, request);
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable Long id) {
        cartProductService.deleteCartProduct(id);
        return ResponseEntity.noContent().build();
    }
}
