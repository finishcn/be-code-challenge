/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.pool;

import cn.hutool.core.util.StrUtil;
import org.example.constant.MappedConstant;
import org.example.mapped.util.TraceIdUtil;
import org.slf4j.MDC;

/**
 * trace thread
 *
 * @author liyu.caelus
 * @version 1.0
 */
public class TaskRunnable implements Runnable {
    private final Runnable task;

    private String traceId;

    TaskRunnable(Runnable task) {
        this.task = task;
    }

    TaskRunnable(Runnable task, String traceId) {
        this.task = task;
        this.traceId = traceId;
    }

    @Override
    public void run() {
        String trace = MDC.get(MappedConstant.TRACE_ID);
        if (StrUtil.isEmpty(trace)) {
            if (StrUtil.isNotEmpty(this.traceId)) {
                MDC.put(MappedConstant.TRACE_ID, traceId);
            } else {
                trace = TraceIdUtil.getGenerateTraceId();
                MDC.put(MappedConstant.TRACE_ID, trace);
            }
        }
        this.task.run();
        MDC.clear();
    }
}

