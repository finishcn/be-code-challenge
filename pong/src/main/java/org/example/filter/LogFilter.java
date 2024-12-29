/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * log filter
 *
 * @author liyu.caelus 2024/12/29
 */
@Slf4j
@Order(2)
@Component
public class LogFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();
        log.info("Request sent: {}", exchange.getRequest().getPath());
        return chain.filter(exchange)
                .doFinally(signalType -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.info("Request processing time: {} ms", duration);
                });
    }
}
