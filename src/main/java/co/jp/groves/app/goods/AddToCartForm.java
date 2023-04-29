package co.jp.groves.app.goods;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
class AddToCartForm {
    @NotNull private Integer goodsId;
    // @Min(1)
    // @Max(50)
    private Integer quantity = 1;
    private Integer categoryId;
}
