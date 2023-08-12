package shopping.application;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartProduct;
import shopping.domain.Member;
import shopping.domain.Product;
import shopping.dto.CartProductRequest;
import shopping.dto.CartProductResponse;
import shopping.exception.MemberException;
import shopping.exception.ProductException;
import shopping.repository.CartProductRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

@Service
@Transactional
public class CartProductService {

    public static final int DEFAULT_PRODUCT_QUANTITY = 1;
    public static final int MIN_CART_PRODUCT_QUANTITY = 1;

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

    public CartProductService(MemberRepository memberRepository, ProductRepository productRepository, CartProductRepository cartRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartRepository;
    }

    public void addProduct(Long memberId, Long productId) {
        Product product = getProduct(productId);
        Member member = getMember(memberId);

        CartProduct cartProduct = cartProductRepository.findOneByMemberIdAndProductId(memberId, productId)
                .map(CartProduct::increaseQuantity)
                .orElseGet(() -> new CartProduct(member, product, DEFAULT_PRODUCT_QUANTITY));

        cartProductRepository.save(cartProduct);
    }

    private Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(
                        MessageFormat.format("회원Id에 해당하는 회원이 존재하지 않습니다 id : {0}", memberId)
                ));
        return member;
    }

    private Product getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(
                        MessageFormat.format("상품Id에 해당하는 상품 존재하지 않습니다 id : {0}", productId)
                ));
        return product;
    }

    public List<CartProductResponse> findCartProducts(Long memberId) {
        return cartProductRepository.findAllByMemberId(memberId)
                .stream()
                .map(CartProductResponse::from)
                .collect(Collectors.toList());
    }

    public void updateCartProduct(Long id, CartProductRequest request) {
        if (request.getQuantity() < MIN_CART_PRODUCT_QUANTITY) {
            cartProductRepository.deleteById(id);
            return;
        }
        cartProductRepository
            .findById(id)
            .ifPresent(cartProduct -> cartProduct.updateQuantity(request.getQuantity()));
    }

    public void deleteCartProduct(Long id) {
        cartProductRepository.deleteById(id);
    }
}
