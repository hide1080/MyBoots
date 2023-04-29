package co.jp.groves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(reg -> reg.requestMatchers("/")
                .permitAll()
                .requestMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**", "/fonts/**")
                .permitAll()
                .requestMatchers("/loginForm")
                .permitAll()
                .requestMatchers("/account/**")
                .permitAll()
                .requestMatchers("/category/**")
                .permitAll()
                .requestMatchers("/goods/**")
                .permitAll()
                .requestMatchers("/search/**")
                .permitAll()
                .requestMatchers("/api/**")
                .permitAll()
                .requestMatchers("/h2-console/**")
                .permitAll()
                .anyRequest()
                .authenticated());

        http.formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/loginForm")
                .failureUrl("/loginForm?error")
                .defaultSuccessUrl("/", false)
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
        http.logout()
                // .logoutUrl("/logout**") // POSTでアクセスしなければならない
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) // GETでも可
                .logoutSuccessUrl("/loginForm");
        var cache = new HttpSessionRequestCache();
        cache.setRequestMatcher(AnyRequestMatcher.INSTANCE);
        http.setSharedObject(RequestCache.class, cache);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.headers().frameOptions().disable();
        http.csrf().ignoringRequestMatchers("/h2-console/**");

        return http.build();
    }

    @Configuration
    static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        private final UserDetailsService userDetailsService;

        public AuthenticationConfiguration(final UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }
    }
}
