package shopping.mart.persist;

import static java.util.function.Function.identity;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Order findOrderById(Long userId, Long orderId) {
        OrderEntity orderEntity = orderJpaRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new BadRequestException(
                        MessageFormat.format("orderId\"{0}\"에 해당하는 내역이 없습니다.", orderId),
                        OrderExceptionStatus.NOT_EXISTS_ORDER.getStatus())
                );

        List<OrderProductEntity> orderProductEntities = orderProductJpaRepository.findByOrderEntity(orderEntity);

        Map<Product, Integer> productCounts = orderProductEntities.stream()
                .collect(Collectors.toMap(product -> new Product(
                        product.getProductId(),
                        product.getName(),
                        product.getImageUrl(),
                        product.getPrice()
                ), OrderProductEntity::getCount));

        return new Order(orderEntity.getId(), productCounts, orderEntity.getCurrencyByUsd());
    }

    public List<Order> findOrderHistory(Long userId) {
        List<OrderEntity> orderEntities = orderJpaRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        List<OrderProductEntity> orderProductEntities = orderProductJpaRepository.findAllByOrderEntityIn(orderEntities);

        Map<OrderEntity, Map<Product, Integer>> mergeTable = orderEntities.stream()
                .collect(Collectors.toMap(identity(), orderEntity -> new HashMap<>()));
        orderProductEntities.forEach(orderProductEntity -> {
            Product product = new Product(
                    orderProductEntity.getProductId(),
                    orderProductEntity.getName(),
                    orderProductEntity.getImageUrl(),
                    orderProductEntity.getPrice()
            );
            mergeTable.get(orderProductEntity.getOrderEntity()).put(product, orderProductEntity.getCount());
        });

        return orderEntities.stream()
                .map(orderEntity -> new Order(
                        orderEntity.getId(), mergeTable.get(orderEntity), orderEntity.getCurrencyByUsd()
                ))
                .collect(Collectors.toList());
    }

    public Long order(Long userId, Order order) {
        OrderEntity orderEntity = new OrderEntity(userId, order.getCurrencyByUsd());
        OrderEntity savedOrder = orderJpaRepository.save(orderEntity);

        List<OrderProductEntity> savedOrderProducts = order.getProductCounts().entrySet().stream()
                .map(item -> new OrderProductEntity(savedOrder, item.getKey().getId(), item.getKey().getName(),
                        item.getKey().getImageUrl(), item.getKey().getPrice(), item.getValue()))
                .collect(Collectors.toList());
        orderProductJpaRepository.saveAll(savedOrderProducts);

        return savedOrder.getId();
    }
}
