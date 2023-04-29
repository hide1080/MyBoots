/**
 * 以下を参考にした。
 * https://github.com/making/jsug-spring-boot-handson
 */
package co.jp.groves.infra.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class HandlerExceptionResolverLoggingAspect {

    @Around("execution(*" + " org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))")
    public Object logException(ProceedingJoinPoint joinPoint) throws Throwable {
        var ret = joinPoint.proceed();
        var request = (HttpServletRequest) joinPoint.getArgs()[0];

        if (request.getAttribute("ERROR_LOGGED") == null) {
            var handler = joinPoint.getArgs()[2];
            var exception = (Exception) joinPoint.getArgs()[3];
            log.info(
                    "Error occurred [url=%s %s, handler=%s]"
                            .formatted(request.getMethod(), request.getRequestURI(), handler),
                    exception);
            // mark as logged
            request.setAttribute("ERROR_LOGGED", true);
        }
        return ret;
    }
}
