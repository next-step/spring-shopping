package shopping.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.product.Product;
import shopping.dto.request.CartProductRequest;
import shopping.repository.CartProductRepository;
import shopping.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class CartProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CartProductRepository cartProductRepository;

    @InjectMocks
    CartProductService cartProductService;

    @Test
    @DisplayName("장바구니 상품을 추가할 수 있다")
    void createCartProduct() {
        /* given */
        final Long memberId = 1L;
        final Long productId = 1L;

        given(productRepository.findById(productId)).willReturn(
            Optional.of(new Product("치킨, ", "/asset/img/chicken", 20000))
        );
        given(cartProductRepository.findByMemberIdAndProductId(memberId, productId)).willReturn(
            Optional.empty()
        );

        /* when */
        cartProductService.createCartProduct(memberId, new CartProductRequest(productId));

        /* then */
        verify(productRepository, atLeast(1)).findById(productId);
        verify(cartProductRepository, atLeast(1))
            .findByMemberIdAndProductId(memberId, productId);
    }

}
