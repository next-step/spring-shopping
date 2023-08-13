package shopping.mart.persist;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shopping.core.entity.OrderEntity;
import shopping.core.entity.OrderProductEntity;
import shopping.core.exception.BadRequestException;
import shopping.mart.domain.Order;
import shopping.mart.domain.Product;
import shopping.mart.domain.status.OrderExceptionStatus;
import shopping.mart.persist.repository.OrderJpaRepository;
import shopping.mart.persist.repository.OrderProductJpaRepository;

@Repository
public class OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderProductJpaRepository orderProductJpaRepository;

    public OrderRepository(OrderJpaRepository orderJpaRepository, OrderProductJpaRepository orderProductJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderProductJpaRepository = orderProductJpaRepository;
    }

    public Order findOrderById(Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestException(MessageFormat.format("orderId\"{0}\"에 해당하는 내역이 없습니다.", orderId),
                        OrderExceptionStatus.NOT_EXISTS_ORDER.getStatus()));

        List<OrderProductEntity> orderProductEntities = orderProductJpaRepository.findByOrderEntity(orderEntity);

        List<Product> products = orderProductEntities.stream()
                .map(orderProductEntity -> new Product(orderProductEntity.getId(), orderProductEntity.getName(),
                        orderProductEntity.getImageUrl(), orderProductEntity.getPrice()))
                .collect(Collectors.toList());

        return new Order(products);
    }

    public Long order(Long userId, Order order) {
        OrderEntity orderEntity = new OrderEntity(userId);
        OrderEntity savedOrder = orderJpaRepository.save(orderEntity);

        List<OrderProductEntity> savedOrderProducts = order.getProducts().stream()
                .map(product -> new OrderProductEntity(savedOrder, product.getId(), product.getName(),
                        product.getImageUrl(), product.getPrice()))
                .collect(Collectors.toList());
        orderProductJpaRepository.saveAll(savedOrderProducts);

        return savedOrder.getId();
    }
}
