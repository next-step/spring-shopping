package shopping.order.repository.entity;


import java.io.Serializable;
import java.util.Objects;

public class ReceiptProductId implements Serializable {

    private ReceiptEntity receiptEntity;
    private Long productId;

    private ReceiptProductId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceiptProductId)) {
            return false;
        }
        ReceiptProductId that = (ReceiptProductId) o;
        return Objects.equals(receiptEntity.getId(), that.receiptEntity.getId()) && Objects.equals(productId,
                that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptEntity.getId(), productId);
    }
}
