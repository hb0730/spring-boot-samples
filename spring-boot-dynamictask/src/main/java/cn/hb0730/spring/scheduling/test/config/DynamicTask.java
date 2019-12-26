package cn.hb0730.spring.scheduling.test.config;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 动态定时任务
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Configuration
public class DynamicTask implements SchedulingConfigurer {
    private static Logger LOGGER = LoggerFactory.getLogger(DynamicTask.class);
    private volatile ScheduledTaskRegistrar registrar;
    private ConcurrentHashMap<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, CronTask> cronTasks = new ConcurrentHashMap<>();

    private volatile List<TaskConstant> taskConstants = Lists.newArrayList();

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        this.registrar = registrar;
        this.registrar.addTriggerTask(() -> {
            if (!CollectionUtils.isEmpty(taskConstants)) {
                LOGGER.info("检测动态定时任务列表...");
                List<SchedulingRunnable> runnables = new ArrayList<>();
                taskConstants.forEach(taskConstant -> {
                    SchedulingRunnable runnable = new SchedulingRunnable(taskConstant.getTaskId(), taskConstant.getCron());
                    runnables.add(runnable);
                });
            }
        }, triggerContext -> new PeriodicTrigger(5L, TimeUnit.SECONDS).nextExecutionTime(triggerContext));
    }

    /**
     * <p>
     * 刷新
     * </p>
     */
    public void refreshTasks(List<SchedulingRunnable> runnables) {
        Set<String> taskIds = scheduledFutures.keySet();
        //取消已经删除的策略任务
        for (String taskId : taskIds) {
            if (!exists(runnables, taskId)) {
                scheduledFutures.remove(taskId).cancel(false);
                cronTasks.remove(taskId);
            }
        }
        for (SchedulingRunnable runnable : runnables) {
            String expression = runnable.getExpression();
            if (StringUtils.isBlank(expression) || !CronSequenceGenerator.isValidExpression(expression)) {
                LOGGER.error("定时任务DynamicTask cron表达式不合法: " + expression);
                continue;
            }
            //配置相同,则不需要重新创建定时任务
            if (scheduledFutures.containsKey(runnable.getTaskId())
                    && cronTasks.get(runnable.getTaskId()).getExpression().equalsIgnoreCase(expression)) {
                continue;
            }
            // 如果策略执行时间发生了变化，则取消当前策略的任务
            if (scheduledFutures.containsKey(runnable.getTaskId())) {
                scheduledFutures.remove(runnable.getTaskId()).cancel(false);
                cronTasks.remove(runnable.getTaskId());
            }
            CronTask task = new CronTask(runnable, expression);
            ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
            cronTasks.put(runnable.getTaskId(), task);
            scheduledFutures.put(runnable.getTaskId(), future);
        }
    }

    /**
     * <p>
     * 是否存在相同任务
     * </p>
     *
     * @param tasks  任务运行
     * @param taskId 任务id
     * @return 是否存在
     */
    private boolean exists(List<SchedulingRunnable> tasks, String taskId) {
        for (SchedulingRunnable task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 获取所有任务
     * </p>
     *
     * @return 任务集
     */
    public List<TaskConstant> getTaskConstants() {
        return taskConstants;
    }

    /**
     * <p>
     * 新增任务
     *
     * </p>
     *
     * @param taskId     任务id
     * @param expression 表达式
     */
    public void addTriggerTask(String taskId, String expression) {
        if (StringUtils.isBlank(expression) || !CronSequenceGenerator.isValidExpression(expression)) {
            LOGGER.error("定时任务DynamicTask cron表达式不合法: " + expression);
            return;
        }
        //配置相同,则不需要重新创建定时任务
        if (scheduledFutures.containsKey(taskId)
                && cronTasks.get(taskId).getExpression().equalsIgnoreCase(expression)) {
            return;
        }
        // 如果策略执行时间发生了变化，则取消当前策略的任务
        if (scheduledFutures.containsKey(taskId)) {
            scheduledFutures.remove(taskId).cancel(false);
            cronTasks.remove(taskId);
        }
        SchedulingRunnable runnable = new SchedulingRunnable(taskId, expression);
        CronTask task = new CronTask(runnable, expression);
        addTriggerTask(taskId, task);
    }

    /**
     * 新增任务
     *
     * @param taskId      任务id
     * @param triggerTask 触发器
     */
    public void addTriggerTask(String taskId, TriggerTask triggerTask) {
        TaskScheduler scheduler = registrar.getScheduler();
        assert scheduler != null;
        ScheduledFuture<?> schedule = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
        assert schedule != null;
        cronTasks.put(taskId, (CronTask) triggerTask);
        scheduledFutures.put(taskId, schedule);
    }

    /**
     * <p>
     * 取消计划
     * </p>
     *
     * @param taskId 任务id
     */
    public void cancelTriggerTask(String taskId) {
        ScheduledFuture<?> future = scheduledFutures.get(taskId);
        if (future != null) {
            future.cancel(false);
        }
        scheduledFutures.remove(taskId).cancel(false);
        cronTasks.remove(taskId);
        List<TaskConstant> list = new ArrayList<>();
        taskConstants.forEach(taskConstant -> {
            if (taskConstant.getTaskId().equals(taskId)) {
                list.add(taskConstant);
            }
        });
        taskConstants.removeAll(list);
    }

    /**
     * <p>
     * 全部取消
     * </p>
     */
    public void cancelTriggerTask() {
        ConcurrentHashMap.KeySetView<String, ScheduledFuture<?>> keys = scheduledFutures.keySet();
        for (String key : keys) {
            cancelTriggerTask(key);
        }
    }

    /**
     * <p>
     * 销毁
     * </p>
     */
    @PreDestroy
    public void destroy() {
        this.registrar.destroy();
    }

    /**
     * <p>
     * 任务编号
     * </p>
     *
     * @return 任务id集
     */
    public Set<String> taskIds() {
        return scheduledFutures.keySet();
    }

    /**
     * <p>
     * 是否存在任务
     * </p>
     *
     * @param taskId 任务id
     * @return 是否存在
     */
    public boolean hasTask(String taskId) {
        return scheduledFutures.containsKey(taskId);
    }

}
