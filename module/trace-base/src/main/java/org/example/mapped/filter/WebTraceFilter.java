/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.filter;


import io.micrometer.common.util.StringUtils;
import org.example.constant.MappedConstant;
import org.example.mapped.util.TraceIdUtil;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * check trace id
 * @author liyu.caelus 2024/12/31
 */
@Order(1)
@Component
public class WebTraceFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (StringUtils.isBlank(MDC.get(MappedConstant.TRACE_ID))) {
            String traceId = TraceIdUtil.getGenerateTraceId();
            MDC.put(MappedConstant.TRACE_ID, traceId);
        }
        return chain.filter(exchange);
    }
}
