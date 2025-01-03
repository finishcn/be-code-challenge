/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.pool;

import jakarta.annotation.Nonnull;
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
    public void execute(@Nonnull Runnable task) {
        super.execute(new TaskRunnable(task));
    }

    @Override
    @Nullable
    public ScheduledFuture<?> schedule(@Nonnull Runnable task,@Nonnull Trigger trigger) {
        return super.schedule(new TaskRunnable(task), trigger);
    }

    @Nonnull
    @Override
    public ScheduledFuture<?> schedule(@Nonnull Runnable task,@Nonnull Date startTime) {
        return super.schedule(new TaskRunnable(task), startTime);
    }

}
