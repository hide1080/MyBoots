package co.jp.groves.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cart implements Serializable {
    private final OrderLines orderLines;

    public Cart() {
        this(new OrderLines());
    }

    public Cart add(OrderLine orderLine) {

        orderLines.stream()
                .filter(x -> Objects.equals(
                        x.getGoods().getGoodsId(), orderLine.getGoods().getGoodsId()))
                .findFirst()
                .ifPresentOrElse(
                        line -> line.setQuantity(line.getQuantity() + orderLine.getQuantity()),
                        () -> orderLines.list.add(orderLine));

        return this;
    }

    public Cart clear() {
        orderLines.list.clear();
        return this;
    }

    public Cart remove(Set<Integer> lineNo) {
        var iterator = getOrderLines().getList().iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            iterator.next();
            if (lineNo.contains(i)) {
                iterator.remove();
            }
        }
        return this;
    }

    public boolean isEmpty() {
        return orderLines.list.isEmpty();
    }
}
