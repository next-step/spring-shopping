package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Cart;
import shopping.domain.Member;
import shopping.domain.Product;
import shopping.exception.MemberException;
import shopping.exception.ProductException;
import shopping.repository.CartRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

@Service
@Transactional
public class CartService {

    public static final int DEFAULT_PRODUCT_QUANTITY = 1;

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartService(MemberRepository memberRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public void addProduct(Long memberId, Long productId) {
    }
}
