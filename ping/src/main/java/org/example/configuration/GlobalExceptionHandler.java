package org.example.configuration;

import cn.hutool.http.HttpException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * Unified exception handling
 *
 * author liyu.caelus 2024/12/31
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * HttpException Handler
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpException.class)
    public Mono<String> HttpExceptionHandler(final HttpException e) {
        log.error("Request sent error", e);
        return Mono.just(e.getMessage());
    }

    /**
     * runtimeException Handler
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Mono<String> runtimeExceptionHandler(final RuntimeException e) {
        log.error("The service encountered an unexpected exception.", e);
        return Mono.just("The service encountered an unexpected exception.");
    }
}

