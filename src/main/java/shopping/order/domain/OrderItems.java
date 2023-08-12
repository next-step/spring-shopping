package shopping.order.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import shopping.common.vo.Money;

@Embeddable
public class OrderItems {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void add(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public Money getAllOrderMoney() {
        return orderItems.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(new Money("0"), Money::add);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
