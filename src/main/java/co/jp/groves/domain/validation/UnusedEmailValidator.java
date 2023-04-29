/**
 * 以下を参考にした。
 * http://terasolunaorg.github.io/guideline/5.0.0.RELEASE/ja/ArchitectureInDetail/Validation.html
 */
package co.jp.groves.domain.validation;

import co.jp.groves.domain.service.account.AccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UnusedEmailValidator implements ConstraintValidator<UnusedEmail, String> {

    private final AccountService accountService;

    UnusedEmailValidator(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void initialize(UnusedEmail constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return accountService.isUnusedEmail(value);
    }
}
