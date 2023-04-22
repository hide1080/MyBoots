package co.jp.groves.domain.service.userdetails;

import co.jp.groves.domain.repository.account.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    ShopUserDetailsService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository
                .findByEmail(username)
                .map(ShopUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " is not found!"));
    }
}
