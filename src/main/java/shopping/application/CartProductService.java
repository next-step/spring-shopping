package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartProduct;
import shopping.domain.Member;
import shopping.domain.Product;
import shopping.dto.FindCartProductResponse;
import shopping.dto.UpdateCartProductRequest;
import shopping.exception.MemberException;
import shopping.exception.ProductException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartProductService {

    public static final int DEFAULT_PRODUCT_QUANTITY = 1;

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

    public CartProductService(MemberRepository memberRepository, ProductRepository productRepository, CartProductRepository cartRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartRepository;
    }

    public void addProduct(Long memberId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("상품Id에 해당하는 상품 존재하지 않습니다"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("회원Id에 해당하는 회원이 존재하지 않습니다"));

        CartProduct cart = cartProductRepository.findOneByMemberIdAndProductId(memberId, productId)
                .map(CartProduct::increaseQuantity)
                .orElseGet(() -> new CartProduct(member, product, DEFAULT_PRODUCT_QUANTITY));

        cartProductRepository.save(cart);
    }

    public List<FindCartProductResponse> findCartProducts(Long memberId) {
        return cartProductRepository.findAllByMemberId(memberId)
                .stream()
                .map(FindCartProductResponse::from)
                .collect(Collectors.toList());
    }

    public void updateCartProduct(Long id, UpdateCartProductRequest request) {
        cartProductRepository.updateById(id, request.getQuantity());
    }

    public void deleteCartProduct(Long id) {
        cartProductRepository.deleteById(id);
    }
}
