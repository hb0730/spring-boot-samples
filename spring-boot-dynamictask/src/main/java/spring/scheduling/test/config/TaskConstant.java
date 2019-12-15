package spring.scheduling.test.config;

import java.util.Objects;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class TaskConstant {
    /**
     * <p>
     * 表达式
     * </P>
     */
    private String cron;
    /**
     * <p>
     * 任务id
     * </p>
     */
    private String taskId;

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskConstant that = (TaskConstant) o;
        return Objects.equals(cron, that.cron) &&
                Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cron, taskId);
    }
}
