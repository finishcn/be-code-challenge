package org.example.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * Unified exception handling
 * <p>
 * author liyu.caelus 2024/12/31
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * runtimeException Handler
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Mono<String> runtimeExceptionHandler(final RuntimeException e) {
        log.error("The service encountered an unexpected exception.", e);
        return Mono.just("The service encountered an unexpected exception.");
    }
}

