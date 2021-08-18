package com.hb0730.spring.boot.redis.stream.queue2.sample.service;

import com.hb0730.spring.boot.redis.stream.queue2.sample.entity.CustomEntity;
import com.hb0730.spring.boot.redis.stream.queue2.sample.listener.AbstractMessageProducerListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author bing_huang
 * @date 2021/8/18
 */
@Service
public class CustomProducerService extends AbstractMessageProducerListener<CustomEntity> {
    public CustomProducerService(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getQueueName() {
        return CustomEntity.QUEUE_NAME;
    }
}
