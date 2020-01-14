package cn.hb0730.spring.boot.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DemoPublisherTest {

    @Autowired
    DemoPublisher demoPublisher;
    @Autowired
    ApplicationEventPublisher publisher;

    @Test
    public void publish() {
        demoPublisher.publish("test");

    }

    @Test
    public void publish2() {
        publisher.publishEvent(new DemoEvent(this, "hello"));
    }
}