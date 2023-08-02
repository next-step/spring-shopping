package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartProduct;
import shopping.domain.Member;
import shopping.domain.Product;
import shopping.exception.MemberException;
import shopping.exception.ProductException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

@Service
@Transactional
public class CartProductService {

    public static final int DEFAULT_PRODUCT_QUANTITY = 1;

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartRepository;

    public CartProductService(MemberRepository memberRepository, ProductRepository productRepository, CartProductRepository cartRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public void addProduct(Long memberId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("상품Id에 해당하는 상품 존재하지 않습니다"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("회원Id에 해당하는 회원이 존재하지 않습니다"));

        CartProduct cart = cartRepository.findByMemberIdAndProductId(memberId, productId)
                .map(CartProduct::increaseQuantity)
                .orElseGet(() -> new CartProduct(member, product, DEFAULT_PRODUCT_QUANTITY));

        cartRepository.save(cart);
    }
}
