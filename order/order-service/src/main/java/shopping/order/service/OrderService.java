package shopping.order.service;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.app.api.cart.CartUseCase;
import shopping.mart.app.api.cart.response.CartResponse;
import shopping.mart.app.api.cart.response.CartResponse.ProductResponse;
import shopping.mart.app.domain.Cart;
import shopping.mart.app.domain.Product;
import shopping.order.app.api.order.OrderUseCase;
import shopping.order.app.api.order.request.OrderRequest;
import shopping.order.app.domain.Order;
import shopping.order.app.domain.Receipt;
import shopping.order.app.spi.ReceiptRepository;

@Service
@Transactional(readOnly = true)
public class OrderService implements OrderUseCase {

    private final CartUseCase cartUseCase;
    private final ReceiptRepository receiptRepository;

    public OrderService(CartUseCase cartUseCase, ReceiptRepository receiptRepository) {
        this.cartUseCase = cartUseCase;
        this.receiptRepository = receiptRepository;
    }

    @Override
    @Transactional
    public long order(OrderRequest orderRequest) {
        CartResponse cartResponse = cartUseCase.getCart(orderRequest.getUserId());

        Order order = new Order(toCart(orderRequest.getUserId(), cartResponse));
        Receipt receipt = order.purchase();

        long receiptId = receiptRepository.persist(receipt);

        cartUseCase.clearCart(orderRequest.getUserId());

        return receiptId;
    }

    private Cart toCart(long userId, CartResponse cartResponse) {
        Cart cart = new Cart(cartResponse.getCartId(), userId);

        Map<Product, Integer> products = cartResponse.getProductResponses()
                .stream()
                .collect(Collectors.toMap(this::toProduct, ProductResponse::getCount));

        products.forEach((key, value) -> {
            cart.addProduct(key);
            cart.updateProduct(key, value);
        });
        return cart;
    }

    private Product toProduct(ProductResponse productResponse) {
        return new Product(productResponse.getId(), productResponse.getName(), productResponse.getImageUrl(),
                productResponse.getPrice());
    }

}
