package cn.hb0730.spring.scheduling.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * <p>
 *     定时任务运行类
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class SchedulingRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SchedulingRunnable.class);
    private String expression;

    private String taskId;

    public SchedulingRunnable() {
    }

    public SchedulingRunnable(String expression, String taskId) {
        this.expression = expression;
        this.taskId = taskId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        logger.info("定时任务开始执行");
        long startTime = System.currentTimeMillis();
        long times = System.currentTimeMillis() - startTime;
        logger.info("定时任务执行结束 {} 毫秒", times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingRunnable that = (SchedulingRunnable) o;
        return Objects.equals(expression, that.expression) &&
                Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, taskId);
    }
}
