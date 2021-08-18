package com.hb0730.spring.boot.redis.stream.queue2.sample.service;

import com.hb0730.spring.boot.redis.stream.queue2.sample.entity.CustomEntity;
import com.hb0730.spring.boot.redis.stream.queue2.sample.listener.IMessageConsumerListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class ConsumerServiceTest {
    @Autowired
    private IMessageConsumerListener<CustomEntity> custom2ConsumerService;

    @Test
    public void consumer() throws Exception {
        custom2ConsumerService.onMessage(null);
        Thread.sleep(Duration.ofSeconds(3).toMillis());
    }
}