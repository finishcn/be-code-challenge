/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.mapped.configuration;

import org.example.mapped.pool.TaskSchedulerThreadPool;
import org.example.mapped.util.SchedulingUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * scheduling configuration
 *
 * @Author liyu.caelus
 * @@version 1.0
 */
@Configuration
public class SchedulingConfiguration implements SchedulingConfigurer {

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        TaskSchedulerThreadPool scheduler = new TaskSchedulerThreadPool();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60 * 3);
        scheduler.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
        SchedulingUtil.setTaskRegistrar(taskRegistrar);
    }
}
