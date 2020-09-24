package com.hb0730.quartz;

/**
 * @author bing_huang
 * @date 2020/06/01 8:05
 * @since V1.0
 */
public class QuartzConstant {
    public static final String DEFAULT_GROUP = "default";

    public static class JobInfo {

        public static final String JOB_NAME = "jobName";
        public static final String JOB_GROUP_NAME = "jobGroupName";
        public static final String DESCRIPTION = "description";
        public static final String JOB_STATUS = "jobStatus";
        public static final String JOB_TIME = "jobTime";
    }

    public static class ParamsName {
        public static final String CLASS_NAME = "class";
        public static final String METHOD_NAME = "method";
        public static final String PARAMS_NAME = "params";
        public static final String TASK_ID = "task_id";
    }
}
