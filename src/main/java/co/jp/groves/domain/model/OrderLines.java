package co.jp.groves.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderLines implements Serializable {
    final List<OrderLine> list;

    public OrderLines() {
        this(new ArrayList<>());
    }

    public OrderLines orderId(Integer orderId) {
        var i = 0;
        for (OrderLine orderLine : list) {
            orderLine.setLineNo(++i);
            orderLine.setOrderId(orderId);
        }
        return this;
    }

    public Stream<OrderLine> stream() {
        return list.stream();
    }

    public long getTotal() {
        return list.stream().mapToLong(OrderLine::getSubtotal).sum();
    }
}
