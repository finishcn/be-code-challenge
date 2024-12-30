package org.example.mapped.interceptor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

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
