package shopping.order.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.app.api.cart.event.CartOrderedEvent;
import shopping.mart.app.domain.Cart;
import shopping.mart.app.spi.CartRepository;
import shopping.order.app.api.OrderUseCase;
import shopping.order.app.domain.Order;
import shopping.order.app.spi.ReceiptRepository;

@Service
@Transactional(readOnly = true)
public class OrderService implements OrderUseCase {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final CartRepository cartRepository;
    private final ReceiptRepository receiptRepository;

    public OrderService(ApplicationEventPublisher applicationEventPublisher, CartRepository cartRepository,
            ReceiptRepository receiptRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.cartRepository = cartRepository;
        this.receiptRepository = receiptRepository;
    }

    @Override
    public void order(long cartId) {
        Cart cart = cartRepository.getById(cartId);

        Order order = new Order(cart);

        receiptRepository.persist(order);

        applicationEventPublisher.publishEvent(new CartOrderedEvent(cartId));
    }
}
