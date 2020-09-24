package com.hb0730.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class QuartzUtilsTest {
    @Autowired
    private Scheduler scheduler;

    @Test
    public void test() throws SchedulerException {
        JobModel model = new JobModel();
        model.setJobName("job");
        model.setCron("0 * * * * ?");
        QuartzUtils.addJob(Job.class, scheduler, model);
        model = new JobModel();
        model.setJobName("job2");
        model.setCron("15 * * * * ?");
        QuartzUtils.addJob(Job.class, scheduler, model);
        model = new JobModel();
        model.setJobName("job3");
        model.setCron("10 * * * * ?");
        QuartzUtils.addJob(Job.class, scheduler, model);
        model = new JobModel();
        model.setJobName("job4");
        model.setCron("20 * * * * ?");
        QuartzUtils.addJob(Job.class, scheduler, model);

        List<Map<String, Object>> maps = QuartzUtils.queryAllJob(scheduler);
        System.out.println(maps.size());
    }
}
