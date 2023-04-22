/**
 * 以下を参考にした。
 * https://github.com/making/jsug-spring-boot-handson
 */
package co.jp.groves.domain.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
@Slf4j
public class SqlFinder {

    @Cacheable("sql")
    public String get(String path) {
        var resource = new ClassPathResource(path);
        log.info("load {}", resource);

        try (var stream = resource.getInputStream()) {
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(path + " is not found!", e);
        }
    }
}
