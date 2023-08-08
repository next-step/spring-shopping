package shopping.domain;

import java.text.MessageFormat;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import shopping.exception.OrderProductException;

@Entity
public class OrderProduct {

    public static final int MIN_PRODUCT_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    protected OrderProduct() {
    }

    public OrderProduct(Long id, Order order, Product product, int quantity) {
        validateOrder(order);
        validateProduct(product);
        validateQuantity(quantity);
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderProduct(Order order, Product product, int quantity) {
        this(null, order, product, quantity);
    }

    private void validateQuantity(int quantity) {
        if (quantity < MIN_PRODUCT_QUANTITY) {
            throw new OrderProductException(
                MessageFormat
                    .format("상품의 개수는 최소 {0}개여야합니다 현재 개수 : {1}개", MIN_PRODUCT_QUANTITY, quantity)
            );
        }
    }

    private void validateProduct(Product product) {
        if (Objects.isNull(product)) {
            throw new OrderProductException("product 가 존재하지 않습니다");
        }
    }

    private void validateOrder(Order order) {
        if (Objects.isNull(order)) {
            throw new OrderProductException("order 가 존재하지 않습니다");
        }
    }

}
