/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.filter;

import com.google.common.util.concurrent.RateLimiter;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.example.constant.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Throttling  Interceptor
 *
 * @author liyu.caelus 2024/12/31
 */
@Order(9)
@Component
public class ThrottlingInterceptor implements WebFilter {

    @Value("${config.rate-limiter:1}")
    private Integer rateLimite;

    @Getter
    private RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
        rateLimiter = RateLimiter.create(rateLimite);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (rateLimiter.tryAcquire()) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(HttpStatus.RATE_LIMITED));
            return Mono.empty();
        }
    }
}
