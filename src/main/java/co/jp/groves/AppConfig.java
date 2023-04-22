package co.jp.groves;

import co.jp.groves.domain.model.Cart;
import co.jp.groves.infra.cart.CachingCart;
import java.util.Arrays;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@EnableCaching
public class AppConfig {

    private final DataSourceProperties dataSourceProperties;

    AppConfig(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create(this.dataSourceProperties.getClassLoader())
                .url(this.dataSourceProperties.getUrl())
                .username(this.dataSourceProperties.getUsername())
                .password(this.dataSourceProperties.getPassword())
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    Cart cart() {
        return new CachingCart();
    }

    @Bean
    CacheManager cacheManager() {
        var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("category"),
                new ConcurrentMapCache("goods"),
                new ConcurrentMapCache("orderLines"),
                new ConcurrentMapCache("prefectures"),
                new ConcurrentMapCache("sql")));
        return cacheManager;
    }
}
