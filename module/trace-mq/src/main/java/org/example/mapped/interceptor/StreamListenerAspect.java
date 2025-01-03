/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.interceptor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * clear message queue trace id
 *
 * @Author liyu.caelus
 * @version 1.0
 */
@Aspect
@Component
public class StreamListenerAspect {

    @Pointcut("@annotation(org.springframework.cloud.stream.annotation.StreamListener)")
    public void pointCut() {
    }

    @AfterReturning(value = "pointCut()")
    public void clear() {
        MDC.clear();
    }
}
