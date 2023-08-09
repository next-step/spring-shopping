package shopping.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String imageFileName;

    private int totalPrice;

    private Double totalPriceUSD;

    private int totalQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;


    protected OrderItemEntity() {
    };

    public OrderItemEntity(Long id, String name, String imageFileName, int totalPrice, Double totalPriceUSD, int totalQuantity,
        OrderEntity order) {
        this.id = id;
        this.name = name;
        this.imageFileName = imageFileName;
        this.totalPriceUSD = totalPriceUSD;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.order = order;
    }

    public static OrderItemEntity from(CartItemEntity cartItem, OrderEntity order) {
        return new OrderItemEntity(
            null,
            cartItem.getProduct().getName(),
            cartItem.getProduct().getImageFileName(),
            cartItem.getProduct().getPrice() * cartItem.getQuantity(),
            0D,
            cartItem.getQuantity(),
            order
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Double getTotalPriceUSD() {
        return totalPriceUSD;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public OrderEntity getOrder() {
        return order;
    }
}
