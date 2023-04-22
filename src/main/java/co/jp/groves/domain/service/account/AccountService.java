package co.jp.groves.domain.service.account;

import co.jp.groves.domain.model.Account;
import co.jp.groves.domain.repository.account.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    AccountService(final AccountRepository accountRepository, final PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Account findById(Integer accountId) {
        return accountRepository.findById(accountId).orElseThrow(IllegalStateException::new);
    }

    @Transactional(readOnly = true)
    public boolean isUnusedEmail(String email) {
        return accountRepository.countByEmail(email) == 0;
    }

    @Transactional(readOnly = false)
    public Account register(Account account, String rawPassword) {
        account.setPassword(passwordEncoder.encode(rawPassword));
        return accountRepository.create(account);
    }
}
