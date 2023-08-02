package shopping.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.dto.request.CartProductRequest;
import shopping.service.CartProductService;

@Controller
@RequestMapping("/cart")
public class CartProductController {

    private final CartProductService cartProductService;

    public CartProductController(final CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> createCartProduct(
        @RequestHeader("memberId") Long memberId,
        @RequestBody final CartProductRequest request
    ) {
        cartProductService.createCartProduct(memberId, request);

        return ResponseEntity.ok().build();
    }
}
