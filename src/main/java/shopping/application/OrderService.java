package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.Cart;
import shopping.domain.cart.CartItemRepository;
import shopping.domain.order.Order;
import shopping.domain.order.OrderRepository;
import shopping.dto.OrderDetailResponse;
import shopping.dto.OrderResponse;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ExchangeRateProvider exchangeRateProvider;

    public OrderService(final CartItemRepository cartItemRepository,
                        final OrderRepository orderRepository,
                        final OrderMapper orderMapper, ExchangeRateProvider exchangeRateProvider) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.exchangeRateProvider = exchangeRateProvider;
    }

    @Transactional
    public Long createFromCart(final Long userId) {
        Cart cart = findCartByUserId(userId);

        double exchangeRate = exchangeRateProvider.getExchangeRate()
                .orElseThrow(() -> new ShoppingException(ErrorType.NO_EXCHANGE_RATE));
        Order order = orderMapper.mapOrderFrom(cart, exchangeRate);

        Long id = orderRepository.save(order).getId();
        cartItemRepository.deleteAll(cart.getItems());

        return id;
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse findById(final Long userId, final Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ShoppingException(ErrorType.ORDER_NO_EXIST, id));

        if (!Objects.equals(order.getUserId(), userId)) {
            throw new ShoppingException(ErrorType.ORDER_UNAUTHORIZED);
        }

        return OrderDetailResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(final Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    private Cart findCartByUserId(final Long userId) {
        return new Cart(cartItemRepository.findAllByUserId(userId), userId);
    }
}
