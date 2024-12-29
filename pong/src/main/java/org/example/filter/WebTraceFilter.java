/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.filter;


import cn.hutool.core.collection.CollectionUtil;
import io.micrometer.common.util.StringUtils;
import org.example.constant.MappedConstant;
import org.example.util.TraceIdUtil;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author liyu.caelus 2024/12/29
 */
@Order(1)
@Component
public class WebTraceFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String traceId = CollectionUtil.getFirst(exchange.getRequest().getHeaders().get(MappedConstant.TRACE_ID));
        if (StringUtils.isBlank(traceId)) {
            traceId = TraceIdUtil.getGenerateTraceId();
        }
        MDC.put(MappedConstant.TRACE_ID, traceId);
        return chain.filter(exchange);
    }
}
