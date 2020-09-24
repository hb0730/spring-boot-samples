package com.hb0730.quartz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author bing_huang
 * @date 2020/05/27 9:09
 * @since V1.0
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JobModel {
    /**
     * 任务id
     */
    private String jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务id
     */
    private String jobGroupName = QuartzConstant.DEFAULT_GROUP;
    /**
     * 类名
     */
    private String beanName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private String params;

    /**
     * 表达式
     */
    private String cron;
}
