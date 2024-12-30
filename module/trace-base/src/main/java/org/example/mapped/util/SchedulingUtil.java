/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.mapped.util;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

import java.util.ArrayList;
import java.util.List;

/**
 * spring scheduling util
 *
 * @author liyu.caelus
 * @version 1.0
 */
public final class SchedulingUtil {

    private static volatile Scheduling scheduling;

    private final static class Scheduling {

        private ScheduledTaskRegistrar taskRegistrar;
        private List<CronTask> cronTaskList;

        private List<TriggerTask> triggerTaskList;

        private List<CronTask> getCronTaskList() {
            if (null == cronTaskList) {
                cronTaskList = new ArrayList<>();
            }
            return cronTaskList;
        }

        private List<TriggerTask> getTriggerTaskList() {
            if (null == triggerTaskList) {
                triggerTaskList = new ArrayList<>();
            }
            return triggerTaskList;
        }

        public void addTriggerTask(TriggerTask task) {
            this.getTriggerTaskList().add(task);
        }

        public void addCronTask(CronTask task) {
            this.getCronTaskList().add(task);
        }

        public void setTaskRegistrar(ScheduledTaskRegistrar taskRegistrar) {
            this.taskRegistrar = taskRegistrar;
        }

        public void refresh() {
            if (null != cronTaskList) {
                for (CronTask task : cronTaskList) {
                    taskRegistrar.addCronTask(task);
                }
                cronTaskList = null;
            }
            if (null != triggerTaskList) {
                for (TriggerTask task : triggerTaskList) {
                    taskRegistrar.addTriggerTask(task);
                }
            }
        }
    }

    private static Scheduling getScheduling() {
        if (null == scheduling) {
            synchronized (SchedulingUtil.class) {
                if (null == scheduling) {
                    scheduling = new Scheduling();
                }
            }
        }
        return scheduling;
    }

    public static void setTaskRegistrar(ScheduledTaskRegistrar taskRegistrar) {
        getScheduling().taskRegistrar = taskRegistrar;
        getScheduling().refresh();
        scheduling = null;
    }

    public static void addCronTask(Runnable task, String expression) {
        getScheduling().addCronTask(new CronTask(task, expression));
    }

    public static void addCronTask(CronTask task) {
        getScheduling().addCronTask(task);
    }

    public static void addTriggerTask(Runnable task, Trigger trigger) {
        getScheduling().addTriggerTask(new TriggerTask(task, trigger));
    }


    public static void addTriggerTask(TriggerTask task) {
        getScheduling().addTriggerTask(task);
    }

    public static void refresh() {
        getScheduling().refresh();
    }
}
