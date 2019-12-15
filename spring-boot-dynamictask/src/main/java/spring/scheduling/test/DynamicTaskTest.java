package spring.scheduling.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.scheduling.test.config.DynamicTask;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *     spring scheduling dynamic
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicTaskTest {
    @Autowired
    private DynamicTask dynamicTask;
    @Test
    public void test() throws InterruptedException {
        dynamicTask.addTriggerTask("test1","0/5 * * * * ?");
        dynamicTask.addTriggerTask("test2","0/5 * * * * ?");
        dynamicTask.addTriggerTask("test3","0/5 * * * * ?");
        dynamicTask.addTriggerTask("test4","0/5 * * * * ?");
        TimeUnit.SECONDS.sleep(40);
        dynamicTask.addTriggerTask("test5","0/5 * * * * ?");
    }
}
