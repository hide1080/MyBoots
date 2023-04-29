/**
 * 以下を参考にした。
 * https://github.com/making/jsug-spring-boot-handson
 */
package co.jp.groves.app.cart;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

@Data
class CartForm implements Serializable {
    @NotEmpty
    @NotNull private Set<Integer> lineNo;
}
