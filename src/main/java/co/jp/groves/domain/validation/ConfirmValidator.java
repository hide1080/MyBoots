/**
 * 以下を参考にした。
 * http://terasolunaorg.github.io/guideline/5.0.0.RELEASE/ja/ArchitectureInDetail/Validation.html
 */
package co.jp.groves.domain.validation;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

public class ConfirmValidator implements ConstraintValidator<Confirm, Object> {

    private String field;

    private String confirmField;

    private String message;

    public void initialize(Confirm constraintAnnotation) {
        field = constraintAnnotation.field();
        confirmField = "confirm" + StringUtils.capitalize(field);
        message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        var beanWrapper = new BeanWrapperImpl(value);
        var fieldValue = beanWrapper.getPropertyValue(field);
        var confirmFieldValue = beanWrapper.getPropertyValue(confirmField);
        if (Objects.equals(fieldValue, confirmFieldValue)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmField)
                    .addConstraintViolation();
            return false;
        }
    }
}
