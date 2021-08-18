package com.hb0730.spring.boot.redis.stream.queue2.sample.service;

import com.hb0730.spring.boot.redis.stream.queue2.sample.entity.CustomEntity;
import com.hb0730.spring.boot.redis.stream.queue2.sample.listener.IMessageProducerListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
public class ProducerServiceTest {
    @Autowired
    private IMessageProducerListener<CustomEntity> service;

    @Test
    public void send() throws Exception {
        CustomEntity entity = CustomEntity.builder()
                .id(1)
                .name("test1")
                .date(LocalDateTime.now())
                .age(18).build();
        service.sendProducerMessage(entity);
        Thread.sleep(Duration.ofSeconds(3).toMillis());
    }
}