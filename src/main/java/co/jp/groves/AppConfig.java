package co.jp.groves;

import co.jp.groves.domain.model.Cart;
import co.jp.groves.infra.cart.CachingCart;
import java.util.Arrays;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
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

    // Tomcatの現行バージョンでは拡張子がmjsのファイルに対して正しいメディアタイプを設定してくれないため自分で設定する。
    // 将来のバージョンでは対応されるだろう
    @Configuration
    public class MimeMappingCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
        @Override
        public void customize(TomcatServletWebServerFactory container) {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            mappings.add("mjs", "text/javascript");
            container.setMimeMappings(mappings);
        }
    }
}
