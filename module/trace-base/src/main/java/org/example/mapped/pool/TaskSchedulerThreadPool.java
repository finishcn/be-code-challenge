/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.mapped.pool;

import org.springframework.lang.Nullable;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * Scheduler thread pool
 *
 * @author liyu.caelus
 * @version 1.0
 */
public class TaskSchedulerThreadPool extends ThreadPoolTaskScheduler {

    @Override
    public void execute(Runnable task) {
        super.execute(new TaskRunnable(task));
    }

    @Override
    @Nullable
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        return super.schedule(new TaskRunnable(task), trigger);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        return super.schedule(new TaskRunnable(task), startTime);
    }

}
