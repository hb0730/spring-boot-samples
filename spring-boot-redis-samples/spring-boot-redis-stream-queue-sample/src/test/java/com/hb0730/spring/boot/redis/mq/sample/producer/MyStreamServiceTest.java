package com.hb0730.spring.boot.redis.mq.sample.producer;

import com.hb0730.spring.boot.redis.mq.sample.RedisMQSampleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisMQSampleApplication.class)
public class MyStreamServiceTest {
    @Autowired
    private MyStreamService service;

    @Test
    public void sendMessageTest() throws InterruptedException {
        service.sendMessage("test");
        Thread.sleep(Duration.ofSeconds(30).toMillis());
    }

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(30).toMillis());
    }

    @Test
    public void sendEventTest() throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("title", "测试");
        service.sendEvent(map);
        Thread.sleep(Duration.ofSeconds(30).toMillis());
    }
}
