package shopping.order.repository.entity;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import shopping.mart.app.domain.Product;
import shopping.order.app.domain.Receipt;
import shopping.order.app.domain.ReceiptProduct;

@Entity
@Table(name = "receipt")
public class ReceiptEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "receipt_product_entities")
    @OneToMany(mappedBy = "receiptEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceiptProductEntity> receiptProductEntities;

    @Column(name = "total_price")
    private String totalPrice;

    private ReceiptEntity() {
    }

    public ReceiptEntity(Receipt receipt) {
        this.receiptProductEntities = receipt.getReceiptProducts().stream()
                .map(receiptProduct -> new ReceiptProductEntity(this, receiptProduct))
                .collect(Collectors.toList());

        this.userId = receipt.getUserId();
        this.totalPrice = receipt.getTotalPrice().toString();
    }

    public Receipt toDomain(List<Product> products) {
        List<ReceiptProduct> receiptProducts = new ArrayList<>();
        for (ReceiptProductEntity receiptProductEntity : receiptProductEntities) {
            Product product = getMatchedProduct(receiptProductEntity, products);
            receiptProducts.add(receiptProductEntity.toDomain(product));
        }
        return new Receipt(id, receiptProducts, new BigInteger(totalPrice));
    }

    private Product getMatchedProduct(ReceiptProductEntity receiptProductEntity, List<Product> products) {
        return products.stream()
                .filter(receiptProductEntity::isMatched)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(
                        MessageFormat.format("ReceiptProductEntity \"{0}\" 와 일치하는 product를 찾을 수 없습니다.",
                                receiptProductEntity)));
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<ReceiptProductEntity> getReceiptProductEntities() {
        return receiptProductEntities;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}
