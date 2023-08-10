package shopping.order.repository.entity;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import shopping.mart.domain.Product;
import shopping.order.domain.ReceiptProduct;

@Entity
@IdClass(ReceiptProductId.class)
@Table(name = "receipt_product")
public class ReceiptProductEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ReceiptEntity receiptEntity;

    @Id
    @JoinColumn(name = "product_id")
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public ReceiptProductEntity() {
    }

    public ReceiptProductEntity(ReceiptEntity receiptEntity, ReceiptProduct receiptProduct) {
        this.receiptEntity = receiptEntity;
        this.productId = receiptProduct.getProductId();
        this.quantity = receiptProduct.getQuantity();
    }

    public ReceiptProduct toDomain(Product product) {
        return new ReceiptProduct(productId, product.getName(), new BigInteger(product.getPrice()),
                product.getImageUrl(), quantity);
    }

    public boolean isMatched(Product product) {
        return product.getId().equals(productId);
    }

    @Override
    public String toString() {
        return "ReceiptProductEntity{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
