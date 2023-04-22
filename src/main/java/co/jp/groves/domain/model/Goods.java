package co.jp.groves.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Goods implements Serializable {
    private Integer goodsId;
    private String goodsName;
    private String description;
    private int price;
    private int state;
    private int deliveryCharge;
    private int deliveryMethod;
    private int deliveryDays;
    private String image;
    private String thumbnail;
    private LocalDate saleEndDate;
    private boolean soldOut;
    private Account account;
    private Category category;
    private Prefecture prefecture;
}
