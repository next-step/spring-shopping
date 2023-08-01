package shopping.domain;

import shopping.exception.CartException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Cart {

    public static final int MIN_PRODUCT_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public Cart(Member member, Product product, int quantity) {
        validateMember(member);
        validateProduct(product);
        validateQuantity(quantity);
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity < MIN_PRODUCT_QUANTITY) {
            throw new CartException("상품의 개수는 최소 1개여야합니다");
        }
    }

    private void validateProduct(Product product) {
        if (Objects.isNull(product)) {
            throw new CartException("product가 존재하지 않습니다");
        }
    }

    private void validateMember(Member member) {
        if (Objects.isNull(member)) {
            throw new CartException("member가 존재하지 않습니다");
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
