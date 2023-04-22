package co.jp.groves.domain.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLine implements Serializable {
    private int orderId;
    private int lineNo;
    private Goods goods;
    private int quantity;
    private int status;

    public long getSubtotal() {
        return quantity * goods.getPrice();
    }
}
