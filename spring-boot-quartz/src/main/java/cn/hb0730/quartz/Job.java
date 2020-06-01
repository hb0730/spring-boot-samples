package cn.hb0730.quartz;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.validation.constraints.NotNull;

/**
 * @author bing_huang
 * @date 2020/06/01 8:03
 * @since V1.0
 */
public class Job extends QuartzJobBean {
    private static final Logger LOGGER = LogManager.getLogger(QuartzJobBean.class);

    @SneakyThrows
    @Override
    protected void executeInternal(@NotNull JobExecutionContext jobExecutionContext) {
        LOGGER.info("job执行了");
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
    }
}
