/**
 * 以下を参考にした。
 * https://github.com/making/jsug-spring-boot-handson
 */
package co.jp.groves.app.cart;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
class CartForm implements Serializable {
    @NotEmpty
    @NotNull private Set<Integer> lineNo;
}
