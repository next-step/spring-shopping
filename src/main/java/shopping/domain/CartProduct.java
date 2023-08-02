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
public class CartProduct {

    public static final int MIN_PRODUCT_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public CartProduct(Member member, Product product, int quantity) {
        validateMember(member);
        validateProduct(product);
        validateQuantity(quantity);
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    protected CartProduct() {

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

    public CartProduct increaseQuantity() {
        quantity++;
        return this;
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
